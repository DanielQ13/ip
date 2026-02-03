package galath.command;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Abstract class representing a user command.
 * All specific commands (AddTodoCommand, DeleteCommand, etc.) inherit from this class.
 *
 * This design follows the Command pattern, allowing for easy extension
 * of new command types without modifying existing code.
 */
public abstract class Command {

    /**
     * Executes the command.
     * Each subclass implements this method to perform its specific action.
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