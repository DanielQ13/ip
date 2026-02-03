package galath.command;

import galath.task.*;
import galath.ui.*;
import galath.storage.*;

/**
 * galath.command.Command to list all tasks.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showMessage("Your task list is empty!");
            return;
        }

        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append("\n     ").append(i + 1).append(".").append(tasks.getTasks().get(i));
        }
        ui.showMessage(message.toString());
    }
}