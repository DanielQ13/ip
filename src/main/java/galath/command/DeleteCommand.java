package galath.command;

import galath.task.*;
import galath.ui.*;
import galath.storage.*;
import galath.exception.*;

import java.io.IOException;

/**
 * galath.command.Command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

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