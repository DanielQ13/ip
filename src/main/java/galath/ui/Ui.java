package galath.ui;

import java.util.Scanner;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showMessage("Hello! I'm Galath\n     What can I do for you?");
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        showMessage("OOPS!!! " + message);
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        System.out.println("Warning: Unable to load data file. Starting with empty task list.");
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        System.out.println("     " + message);
    }

    /**
     * Reads a command from the user.
     *
     * @return The command string entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}