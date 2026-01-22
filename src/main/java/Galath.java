import java.util.Scanner;

public class Galath {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Print greeting
        printMessage("Hello! I'm Galath\n" + "What can I do for you?");

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
                handleMark(command);
            } else if (command.startsWith("unmark ")) {
                handleUnmark(command);
            }  else if (command.startsWith("todo ")) {
                handleTodo(command);
            } else if (command.startsWith("deadline ")) {
                handleDeadline(command);
            } else if (command.startsWith("event ")) {
                handleEvent(command);
            } else {
                printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }

        scanner.close();
    }

    // Handles todo command
    private static void handleTodo(String command) {
        String description = command.substring(5).trim();
        Task task = new Todo(description);
        tasks[taskCount] = task;
        taskCount++;
        printMessage("Got it. I've added this task:\n      " + task + "\n    Now you have " + taskCount + " tasks in the list.");
    }

    // Handles deadline command
    private static void handleDeadline(String command) {
        String[] parts = command.substring(9).split(" /by ");
        if (parts.length == 2) {
            String description = parts[0].trim();
            String by = parts[1].trim();
            Task task = new Deadline(description, by);
            tasks[taskCount] = task;
            taskCount++;
            printMessage("Got it. I've added this task:\n      " + task + "\n    Now you have " + taskCount + " tasks in the list.");
        }
    }

    // Handles event command
    private static void handleEvent(String command) {
        String[] parts = command.substring(6).split(" /from | /to ");
        if (parts.length == 3) {
            String description = parts[0].trim();
            String from = parts[1].trim();
            String to = parts[2].trim();
            Task task = new Event(description, from, to);
            tasks[taskCount] = task;
            taskCount++;
            printMessage("Got it. I've added this task:\n      " + task + "\n    Now you have " + taskCount + " tasks in the list.");
        }
    }

    // Displays all tasks in the list
    private static void listTasks() {
        StringBuilder taskList = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            taskList.append("\n    ").append((i + 1)).append(".").append(tasks[i]);
        }
        printMessage(taskList.toString());
    }

    // Marks a task as done
    private static void handleMark(String command) {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                tasks[taskIndex].markAsDone();
                printMessage("Nice! I've marked this task as done:\n      " + tasks[taskIndex]);
            }
        } catch (NumberFormatException e) {
            printMessage("Invalid task number!");
        }
    }

    // Marks a task as not done
    private static void handleUnmark(String command) {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                tasks[taskIndex].markAsNotDone();
                printMessage("OK, I've marked this task as not done yet:\n      " + tasks[taskIndex]);
            }
        } catch (NumberFormatException e) {
            printMessage("Invalid task number!");
        }
    }

    private static void printMessage(String message) {
        System.out.println("    " + message);
    }
}