package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * galath.command.Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task task = tasks.getTask(taskIndex);
        task.markAsDone();

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save tasks to file.");
        }

        ui.showMessage("Nice! I've marked this task as done:\n       " + task);
    }
}