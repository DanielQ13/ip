package galath.command;

import java.util.ArrayList;

import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Command to find tasks by searching for a keyword in task descriptions.
 * The search is case-insensitive and matches any part of the description.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand with the specified search keyword.
     *
     * @param keyword The keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the command to find tasks matching the keyword.
     * Displays all tasks whose descriptions contain the keyword (case-insensitive).
     *
     * @param tasks   The task list to search
     * @param ui      The UI to display the results
     * @param storage The storage (not used)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No matching tasks found.");
        } else {
            StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                message.append("\n     ").append(i + 1).append(".").append(matchingTasks.get(i));
            }
            ui.showMessage(message.toString());
        }
    }
}
