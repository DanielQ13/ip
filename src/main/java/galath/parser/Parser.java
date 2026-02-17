package galath.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import galath.command.AddDeadlineCommand;
import galath.command.AddEventCommand;
import galath.command.AddTodoCommand;
import galath.command.Command;
import galath.command.DeleteCommand;
import galath.command.ExitCommand;
import galath.command.FindCommand;
import galath.command.FindOnCommand;
import galath.command.ListCommand;
import galath.command.MarkCommand;
import galath.command.UnmarkCommand;
import galath.exception.GalathException;

/**
 * Parses user input commands and converts them into Command objects.
 *
 * Supports the following commands (with aliases):
 * - bye (exit): Exit the program
 * - list (l, ls): List all tasks
 * - todo (t): Add a todo task
 * - deadline (d): Add a deadline task
 * - event (e): Add an event task
 * - mark (m): Mark a task as done
 * - unmark (u): Mark a task as not done
 * - delete (del, rm): Delete a task
 * - find (f): Find tasks containing keyword
 * - on: Find tasks on a specific date
 */
public class Parser {

    /**
     * Parses a user command and returns the appropriate Command object.
     * Supports command aliases for more flexible syntax.
     *
     * @param fullCommand The full command string from the user
     * @return The parsed Command object ready to be executed
     * @throws GalathException if the command is invalid or malformed
     */
    public static Command parse(String fullCommand) throws GalathException {
        String trimmedCommand = fullCommand.trim();

        // Normalize command by expanding aliases
        String normalizedCommand = expandAlias(trimmedCommand);

        String[] parts = normalizedCommand.split(" ", 2);
        String command = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "bye":
            case "exit":
                return new ExitCommand();

            case "list":
                return new ListCommand();

            case "mark":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "Please specify which task to mark.\n     Example: mark 2 or m 2");
                }
                return parseMarkCommand(normalizedCommand);

            case "unmark":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "Please specify which task to unmark.\n     Example: unmark 2 or u 2");
                }
                return parseUnmarkCommand(normalizedCommand);

            case "delete":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "Please specify which task to delete.\n     Example: delete 3 or del 3");
                }
                return parseDeleteCommand(normalizedCommand);

            case "todo":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "The description of a todo cannot be empty.\n     Example: todo borrow book or t borrow book");
                }
                return parseTodoCommand(normalizedCommand);

            case "deadline":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "The description of a deadline cannot be empty.\n     Example: deadline return book /by 2019-12-02 or d return book /by 2019-12-02");
                }
                return parseDeadlineCommand(normalizedCommand);

            case "event":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "The description of an event cannot be empty.\n     Example: event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
                }
                return parseEventCommand(normalizedCommand);

            case "on":
                return parseOnCommand(normalizedCommand);

            case "find":
                if (args.isEmpty()) {
                    throw new GalathException(
                            "Please specify a keyword to search for.\n     Example: find book or f book");
                }
                return parseFindCommand(normalizedCommand);

            default:
                throw new GalathException("I'm sorry, but I don't know what that means :-(");
        }

    }

    /**
     * Expands command aliases to their full forms.
     *
     * @param command The command string (possibly with alias)
     * @return The command with alias expanded to full form
     */
    private static String expandAlias(String command) {
        // Check if command starts with an alias
        if (command.equals("t") || command.startsWith("t ")) {
            return command.replaceFirst("^t", "todo");
        } else if (command.equals("d") || command.startsWith("d ")) {
            return command.replaceFirst("^d", "deadline");
        } else if (command.equals("e") || command.startsWith("e ")) {
            return command.replaceFirst("^e", "event");
        } else if (command.equals("l") || command.equals("ls")) {
            return "list";
        } else if (command.equals("m") || command.startsWith("m ")) {
            return command.replaceFirst("^m", "mark");
        } else if (command.equals("u") || command.startsWith("u ")) {
            return command.replaceFirst("^u", "unmark");
        } else if (command.equals("del") || command.startsWith("del ")
                || command.equals("rm") || command.startsWith("rm ")) {
            return command.replaceFirst("^(del|rm)", "delete");
        } else if (command.equals("f") || command.startsWith("f ")) {
            return command.replaceFirst("^f", "find");
        } else if (command.equals("exit")) {
            return "bye";
        }
        return command;
    }

    private static Command parseMarkCommand(String command) throws GalathException {
        String numberStr = command.substring(5).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to mark.\n     Example: mark 2");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            return new MarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: mark 2");
        }
    }

    private static Command parseUnmarkCommand(String command) throws GalathException {
        String numberStr = command.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to unmark.\n     Example: unmark 2");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            return new UnmarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: unmark 2");
        }
    }

    private static Command parseDeleteCommand(String command) throws GalathException {
        String numberStr = command.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new GalathException("Please specify which task to delete.\n     Example: delete 3");
        }
        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new GalathException("Invalid task number. Please provide a valid number.\n     Example: delete 3");
        }
    }

    private static Command parseTodoCommand(String command) throws GalathException {
        String description = command.substring(5).trim();
        if (description.isEmpty()) {
            throw new GalathException("The description of a todo cannot be empty.");
        }
        return new AddTodoCommand(description);
    }

    private static Command parseDeadlineCommand(String command) throws GalathException {
        String input = command.substring(9).trim();
        if (input.isEmpty()) {
            throw new GalathException("The description of a deadline cannot be empty.");
        }
        if (!input.contains("/by")) {
            throw new GalathException("Please specify when the deadline is due using '/by'.\n     Example: deadline return book /by 2019-12-02 or deadline return book /by 2019-12-02 1800");
        }
        String[] parts = input.split(" /by ", 2);
        if (parts.length != 2) {
            throw new GalathException("Invalid deadline format. Use: deadline <description> /by <date>\n     Example: deadline return book /by 2019-12-02");
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new GalathException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new GalathException("Please specify when the deadline is due.");
        }
        return new AddDeadlineCommand(description, by);
    }

    private static Command parseEventCommand(String command) throws GalathException {
        String input = command.substring(6).trim();
        if (input.isEmpty()) {
            throw new GalathException("The description of an event cannot be empty.");
        }
        if (!input.contains("/from")) {
            throw new GalathException("Please specify when the event starts using '/from'.\n     Example: event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        }
        if (!input.contains("/to")) {
            throw new GalathException("Please specify when the event ends using '/to'.\n     Example: event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        }
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length != 3) {
            throw new GalathException("Invalid event format. Use: event <description> /from <start> /to <end>\n     Example: event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
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
        return new AddEventCommand(description, from, to);
    }

    private static Command parseOnCommand(String command) throws GalathException {
        String dateStr = command.substring(3).trim();
        if (dateStr.isEmpty()) {
            throw new GalathException("Please specify a date.\n     Example: on 2019-12-02");
        }
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return new FindOnCommand(date);
        } catch (DateTimeParseException e) {
            throw new GalathException("Invalid date format. Please use: yyyy-MM-dd\n     Example: on 2019-12-02");
        }
    }

    private static Command parseFindCommand(String command) throws GalathException {
        String keyword = command.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new GalathException("Please specify a keyword to search for.\n     Example: find book");
        }
        return new FindCommand(keyword);
    }
}