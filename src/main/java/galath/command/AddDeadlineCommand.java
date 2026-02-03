package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Deadline;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * galath.command.Command to add a deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save tasks to file.");
        }

        ui.showMessage("Got it. I've added this task:\n       " + task +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }
}