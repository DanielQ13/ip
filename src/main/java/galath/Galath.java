package galath;

import galath.command.Command;
import galath.exception.GalathException;
import galath.parser.Parser;
import galath.storage.Storage;
import galath.task.TaskList;
import galath.ui.Ui;

/**
 * Main class for the Galath chatbot.
 */
public class Galath {
    private static final String FILE_PATH = "./data/galath.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Galath instance with the specified file path.
     *
     * @param filePath The path to the data file
     */
    public Galath(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Gets the task list.
     *
     * @return The TaskList instance
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Gets the storage instance.
     *
     * @return The Storage instance
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Runs the main program loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (GalathException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new Galath(FILE_PATH).run();
    }
}