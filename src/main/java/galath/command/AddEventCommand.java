package galath.command;

import java.io.IOException;

import galath.exception.GalathException;
import galath.storage.Storage;
import galath.task.Event;
import galath.task.Task;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * galath.command.Command to add an event task.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GalathException {
        Task task = new Event(description, from, to);
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