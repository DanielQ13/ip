package galath.command;

import galath.storage.Storage;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Command to exit the program.
 * Displays a goodbye message and terminates the main loop.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by displaying a goodbye message.
     *
     * @param tasks The task list (not used)
     * @param ui The UI to display the goodbye message
     * @param storage The storage (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Indicates that this command will exit the program.
     *
     * @return true, as this is an exit command
     */
    @Override
    public boolean isExit() {
        return true;
    }
}