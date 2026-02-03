package galath.command;

import galath.task.Task;
import galath.task.TaskList;
import galath.command.Command;
import galath.ui.Ui;
import galath.storage.Storage;
import galath.exception.GalathException;

import java.io.IOException;

/**
 * Command to delete a task from the task list.
 * Removes the task permanently and saves the changes.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a DeleteCommand for the specified task index.
     *
     * @param taskIndex The index of the task to delete (0-based)
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the command to delete a task.
     * Removes the task from the list, saves to storage, and displays a confirmation.
     *
     * @param tasks The task list to delete from
     * @param ui The UI to display the confirmation message
     * @param storage The storage to save the updated task list
     * @throws GalathException if the task index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task removedTask = tasks.deleteTask(taskIndex);

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save tasks to file.");
        }

        ui.showMessage("Noted. I've removed this task:\n       " + removedTask +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }
}