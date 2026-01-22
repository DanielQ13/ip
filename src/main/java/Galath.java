import java.util.Scanner;

public class Galath {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMessage("Hello! I'm Galath\n" + "What can I do for you?");

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
            } else {
                addTask(command);
            }
        }

        scanner.close();
    }

    private static void addTask(String description) {
        tasks[taskCount] = new Task(description);
        taskCount++;
        printMessage("added: " + description);
    }

    private static void listTasks() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < taskCount; i++) {
            taskList.append((i + 1)).append(". ").append(tasks[i]);
            if (i < taskCount - 1) {
                taskList.append("\n    ");
            }
        }
        printMessage(taskList.toString());
    }

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