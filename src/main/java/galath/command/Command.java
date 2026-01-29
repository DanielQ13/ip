package galath.command;

import galath.task.TaskList;
import galath.ui.Ui;
import galath.storage.Storage;
import galath.exception.GalathException;
/**
 * Abstract class representing a command.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks The task list to operate on
     * @param ui The UI to interact with the user
     * @param storage The storage to save tasks
     * @throws GalathException if an error occurs during execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException;

    /**
     * Checks if this command will exit the program.
     *
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}