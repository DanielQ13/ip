import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Galath {
    private static final String LINE = "    ____________________________________________________________";
    private static final String FILE_PATH = "./data/galath.txt";
    private static ArrayList<Task> tasks;
    private static Storage storage;

    public static void main(String[] args) {
        // Initialize storage and load tasks
        storage = new Storage(FILE_PATH);
        tasks = storage.load();

        Scanner scanner = new Scanner(System.in);

        // Print greeting
        printMessage("Hello! I'm Galath\n     What can I do for you?");

        // Main loop
        String command;
        while (true) {
            command = scanner.nextLine();

            if (command.equals("bye")) {
                printMessage("Bye. Hope to see you again soon!");
                break;
            } else if (command.equals("list")) {
                listTasks();
            } else if (command.startsWith("mark ")) {
                try {
                    handleMark(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("mark")) {
                printMessage("OOPS!!! Please specify which task to mark.\n     Example: mark 2");
            } else if (command.startsWith("unmark ")) {
                try {
                    handleUnmark(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("unmark")) {
                printMessage("OOPS!!! Please specify which task to unmark.\n     Example: unmark 2");
            } else if (command.startsWith("delete ")) {
                try {
                    handleDelete(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("delete")) {
                printMessage("OOPS!!! Please specify which task to delete.\n     Example: delete 3");
            } else if (command.startsWith("todo ")) {
                try {
                    handleTodo(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("todo")) {
                printMessage("OOPS!!! The description of a todo cannot be empty.\n     Example: todo borrow book");
            } else if (command.startsWith("deadline ")) {
                try {
                    handleDeadline(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("deadline")) {
                printMessage("OOPS!!! The description of a deadline cannot be empty.\n     Example: deadline return book /by Sunday");
            } else if (command.startsWith("event ")) {
                try {
                    handleEvent(command);
                } catch (GalathException e) {
                    printMessage("OOPS!!! " + e.getMessage());
                }
            } else if (command.equals("event")) {
                printMessage("OOPS!!! The description of an event cannot be empty.\n     Example: event project meeting /from Mon 2pm /to 4pm");
            } else {
                printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }

        scanner.close();
    }

    /**
     * Handles the todo command
     * @param command The todo command
     * @throws GalathException if the description is empty
     */
    private static void handleTodo(String command) throws GalathException {
        String description = command.substring(5).trim();
        if (description.isEmpty()) {
            throw new GalathException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(description);
        tasks.add(task);
        saveTasks();
        printMessage("Got it. I've added this task:\n       " + task + "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles the deadline command
     * @param command The deadline command
     * @throws GalathException if the description is empty or /by is missing
     */
    private static void handleDeadline(String command) throws GalathException {
        String input = command.substring(9).trim();
        if (input.isEmpty()) {
            throw new GalathException("The description of a deadline cannot be empty.");
        }
        if (!input.contains("/by")) {
            throw new GalathException("Please specify when the deadline is due using '/by'.\n     Example: deadline return book /by Sunday");
        }
        String[] parts = input.split(" /by ", 2);
        if (parts.length != 2) {
            throw new GalathException("Invalid deadline format. Use: deadline <description> /by <date>");
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new GalathException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new GalathException("Please specify when the deadline is due.");
        }
        Task task = new Deadline(description, by);
        tasks.add(task);
        saveTasks();
        printMessage("Got it. I've added this task:\n       " + task + "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles the event command
     * @param command The event command
     * @throws GalathException if the description is empty or /from or /to is missing
     */
    private static void handleEvent(String command) throws GalathException {
        String input = command.substring(6).trim();
        if (input.isEmpty()) {
            throw new GalathException("The description of an event cannot be empty.");
        }
        if (!input.contains("/from")) {
            throw new GalathException("Please specify when the event starts using '/from'.\n     Example: event project meeting /from Mon 2pm /to 4pm");
        }
        if (!input.contains("/to")) {
            throw new GalathException("Please specify when the event ends using '/to'.\n     Example: event project meeting /from Mon 2pm /to 4pm");
        }
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length != 3) {
            throw new GalathException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (description.isEmpty()) {
            throw new GalathException("The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new GalathException("Please specify when the event starts.");
        }
        if (to.isEmpty()) {
            throw new GalathException("Please specify when the event ends.");
        }
        Task task = new Event(description, from, to);
        tasks.add(task);
        saveTasks();
        printMessage("Got it. I've added this task:\n       " + task + "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Displays all tasks in the list
     */
    private static void listTasks() {
        StringBuilder taskList = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append("\n     ").append((i + 1)).append(".").append(tasks.get(i));
        }
        printMessage(taskList.toString());
    }

    /**
     * Marks a task as done
     * @param command The mark command with task number
     * @throws GalathException if the task number is invalid
     */
    private static void handleMark(String command) throws GalathException {
        String numberStr = command.substring(5).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to mark.\n     Example: mark 2");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new GalathException("Task number " + (taskIndex + 1) + " does not exist. You have " + tasks.size() + " task(s) in your list.");
            }
            tasks.get(taskIndex).markAsDone();
            saveTasks();
            printMessage("Nice! I've marked this task as done:\n       " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: mark 2");
        }
    }

    /**
     * Marks a task as not done
     * @param command The unmark command with task number
     * @throws GalathException if the task number is invalid
     */
    private static void handleUnmark(String command) throws GalathException {
        String numberStr = command.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to unmark.\n     Example: unmark 2");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new GalathException("Task number " + (taskIndex + 1) + " does not exist. You have " + tasks.size() + " task(s) in your list.");
            }
            tasks.get(taskIndex).markAsNotDone();
            saveTasks();
            printMessage("OK, I've marked this task as not done yet:\n       " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: unmark 2");
        }
    }

    /**
     * Deletes a task from the list
     * @param command The delete command with task number
     * @throws GalathException if the task number is invalid
     */
    private static void handleDelete(String command) throws GalathException {
        String numberStr = command.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to delete.\n     Example: delete 3");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new GalathException("Task number " + (taskIndex + 1) + " does not exist. You have " + tasks.size() + " task(s) in your list.");
            }
            Task removedTask = tasks.remove(taskIndex);
            saveTasks();
            printMessage("Noted. I've removed this task:\n       " + removedTask + "\n     Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: delete 3");
        }
    }

    /**
     * Saves the current task list to the data file.
     */
    private static void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("Warning: Unable to save tasks to file.");
        }
    }

    /**
     * Prints a message with horizontal line borders
     * @param message The message to print
     */
    private static void printMessage(String message) {
        System.out.println("     " + message);
    }
}