package galath.command;

import galath.command.Command;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;
import galath.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Command to find and display tasks occurring on a specific date.
 * Searches for deadlines on that date and events spanning that date.
 */
public class FindOnCommand extends Command {
    private final LocalDate date;

    /**
     * Creates a FindOnCommand for the specified date.
     *
     * @param date The date to search for tasks
     */
    public FindOnCommand(LocalDate date) {
        this.date = date;
    }

    /**
     * Executes the command to find tasks on the specified date.
     * Displays all matching deadlines and events.
     *
     * @param tasks The task list to search
     * @param ui The UI to display the results
     * @param storage The storage (not used)
     */
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