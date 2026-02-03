package galath.command;

import galath.storage.Storage;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * galath.command.Command to exit the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}