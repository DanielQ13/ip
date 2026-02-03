package galath.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * galath.command.Command to find tasks on a specific date.
 */
public class FindOnCommand extends Command {
    private final LocalDate date;

    public FindOnCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.getTasksOnDate(date);

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No tasks found on " +
                    date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks on " +
                    date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
            for (int i = 0; i < matchingTasks.size(); i++) {
                message.append("\n     ").append(i + 1).append(".").append(matchingTasks.get(i));
            }
            ui.showMessage(message.toString());
        }
    }
}