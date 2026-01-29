package galath.command;

import galath.command.Command;
import galath.task.*;
import galath.ui.Ui;
import galath.storage.Storage;

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