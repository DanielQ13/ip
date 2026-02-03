package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Command to mark a task as done.
 * Changes the task's completion status to done and saves the changes.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a MarkCommand for the specified task index.
     *
     * @param taskIndex The index of the task to mark (0-based)
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to mark a task as done.
     * Marks the task, saves to storage, and displays a confirmation.
     *
     * @param tasks The task list containing the task to mark
     * @param ui The UI to display the confirmation message
     * @param storage The storage to save the updated task list
     * @throws GalathException if the task index is invalid
     */
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