package galath.command;

import galath.task.TaskList;
import galath.ui.Ui;
import galath.storage.Storage;
import galath.exception.GalathException;

import java.io.IOException;

/**
 * Command to sort tasks in the task list.
 * Supports the following sort types:
 * - "name": alphabetically by description (all tasks)
 * - "deadline": chronologically by due date (deadlines only)
 * - "event": chronologically by start date (events only)
 * - "type": groups tasks by type (todo, deadline, event)
 */
public class SortCommand extends Command {

    /** Represents the type of sort to perform. */
    public enum SortType {
        NAME, DEADLINE, EVENT, TYPE
    }

    private final SortType sortType;

    /**
     * Creates a SortCommand with the specified sort type.
     *
     * @param sortType The type of sort to apply
     */
    public SortCommand(SortType sortType) {
        assert sortType != null : "Sort type should not be null";
        this.sortType = sortType;
    }

    /**
     * Executes the sort command, sorts the task list in place, saves, and
     * displays the sorted list.
     *
     * @param tasks   The task list to sort
     * @param ui      The UI to display results
     * @param storage The storage to persist the new order
     * @throws GalathException if an error occurs
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        assert tasks != null : "Task list should not be null";

        if (tasks.size() == 0) {
            ui.showMessage("Your task list is empty — nothing to sort!");
            return;
        }

        switch (sortType) {
            case NAME:
                tasks.sortByName();
                ui.showMessage("Sorted all tasks alphabetically by name.");
                break;
            case DEADLINE:
                tasks.sortDeadlines();
                ui.showMessage("Sorted deadlines chronologically.");
                break;
            case EVENT:
                tasks.sortEvents();
                ui.showMessage("Sorted events chronologically by start date.");
                break;
            case TYPE:
                tasks.sortByType();
                ui.showMessage("Sorted tasks by type: Todos, Deadlines, Events.");
                break;
            default:
                throw new GalathException("Unknown sort type.");
        }

        // Display sorted list
        StringBuilder message = new StringBuilder("\n     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append("\n     ").append(i + 1).append(". ").append(tasks.getTasks().get(i));
        }
        ui.showMessage(message.toString());

        // Persist new order
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save sorted tasks to file.");
        }
    }
}