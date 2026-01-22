import java.util.Scanner;

public class Galath {
    private static String[] tasks = new String[100];
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
            } else {
                addTask(command);
            }
        }

        scanner.close();
    }

    private static void addTask(String task) {
        tasks[taskCount] = task;
        taskCount++;
        printMessage("added: " + task);
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
    
    private static void printMessage(String message) {
        System.out.println("    " + message);
    }
}