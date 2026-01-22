import java.util.Scanner;

public class Galath {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMessage("Hello! I'm Galath\n" + "What can I do for you?");

        String command;
        while (true) {
            command = scanner.nextLine();

            if (command.equals("bye")) {
                printMessage("Bye. Hope to see you again soon!");
                break;
            } else {
                printMessage(command);
            }
        }

        scanner.close();
    }

    private static void printMessage(String message) {
        System.out.println("    " + message);
    }
}
