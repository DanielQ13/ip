package galath.command;

import galath.task.*;
import galath.ui.*;
import galath.storage.*;
import galath.exception.*;

import java.io.IOException;

/**
 * galath.command.Command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task task = tasks.getTask(taskIndex);
        task.markAsNotDone();

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save tasks to file.");
        }

        ui.showMessage("OK, I've marked this task as not done yet:\n       " + task);
    }
}