package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Task;
import galath.task.TaskList;
import galath.task.Todo;
import galath.ui.Ui;

/**
 * galath.command.Command to add a todo task.
 */
public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

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