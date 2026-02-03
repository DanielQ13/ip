package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.task.Todo;
import galath.ui.Ui;

/**
 * Command to add a todo task to the task list.
 * A todo is a simple task without any date/time constraints.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Creates an AddTodoCommand with the specified description.
     *
     * @param description The description of the todo task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command to add a todo task.
     * Adds the task to the list, saves to storage, and displays a confirmation.
     *
     * @param tasks The task list to add to
     * @param ui The UI to display the confirmation message
     * @param storage The storage to save the updated task list
     * @throws GalathException if an error occurs during task creation
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task task = new Todo(description);
        tasks.addTask(task);

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showMessage("Warning: Unable to save tasks to file.");
        }

        ui.showMessage("Got it. I've added this task:\n       " + task +
                "\n     Now you have " + tasks.size() + " tasks in the list.");
    }
}