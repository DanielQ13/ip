package galath.command;

import galath.storage.Storage;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Command to list all tasks in the task list.
 * Displays all tasks with their index numbers and completion status.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     * If the task list is empty, displays a message indicating so.
     *
     * @param tasks The task list to display
     * @param ui The UI to display the task list
     * @param storage The storage (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showMessage("Your task list is empty!");
            return;
        }

        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size (); i++) {
            message.append("\n     ").append(i + 1).append(". ").append(tasks.getTasks().get(i));
        }
        ui.showMessage(message.toString());
    }
}