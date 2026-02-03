package galath.parser;

import galath.command.Command;
import galath.command.FindOnCommand;
import galath.command.ExitCommand;
import galath.command.ListCommand;
import galath.command.UnmarkCommand;
import galath.command.DeleteCommand;
import galath.command.AddTodoCommand;
import galath.command.MarkCommand;
import galath.command.AddDeadlineCommand;
import galath.command.AddEventCommand;
import galath.exception.GalathException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input commands and converts them into Command objects.
 *
 * Supports the following commands:
 * - bye: Exit the program
 * - list: List all tasks
 * - todo DESCRIPTION: Add a todo task
 * - deadline DESCRIPTION /by DATE: Add a deadline task
 * - event DESCRIPTION /from START /to END: Add an event task
 * - mark INDEX: Mark a task as done
 * - unmark INDEX: Mark a task as not done
 * - delete INDEX: Delete a task
 * - on DATE: Find tasks on a specific date
 */
public class Parser {

    /**
     * Parses a user command and returns the appropriate Command object.
     *
     * @param fullCommand The full command string from the user
     * @return The parsed Command object ready to be executed
     * @throws GalathException if the command is invalid or malformed
     */
    public static Command parse(String fullCommand) throws GalathException {
        String trimmedCommand = fullCommand.trim();

        if (trimmedCommand.equals("bye")) {
            return new ExitCommand();
        } else if (trimmedCommand.equals("list")) {
            return new ListCommand();
        } else if (trimmedCommand.startsWith("mark ")) {
            return parseMarkCommand(trimmedCommand);
        } else if (trimmedCommand.equals("mark")) {
            throw new GalathException("Please specify which task to mark.\n     Example: mark 2");
        } else if (trimmedCommand.startsWith("unmark ")) {
            return parseUnmarkCommand(trimmedCommand);
        } else if (trimmedCommand.equals("unmark")) {
            throw new GalathException("Please specify which task to unmark.\n     Example: unmark 2");
        } else if (trimmedCommand.startsWith("delete ")) {
            return parseDeleteCommand(trimmedCommand);
        } else if (trimmedCommand.equals("delete")) {
            throw new GalathException("Please specify which task to delete.\n     Example: delete 3");
        } else if (trimmedCommand.startsWith("todo ")) {
            return parseTodoCommand(trimmedCommand);
        } else if (trimmedCommand.equals("todo")) {
            throw new GalathException("The description of a todo cannot be empty.\n     Example: todo borrow book");
        } else if (trimmedCommand.startsWith("deadline ")) {
            return parseDeadlineCommand(trimmedCommand);
        } else if (trimmedCommand.equals("deadline")) {
            throw new GalathException("The description of a deadline cannot be empty.\n     Example: deadline return book /by 2019-12-02 or deadline return book /by 2019-12-02 1800");
        } else if (trimmedCommand.startsWith("event ")) {
            return parseEventCommand(trimmedCommand);
        } else if (trimmedCommand.equals("event")) {
            throw new GalathException("The description of an event cannot be empty.\n     Example: event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        } else if (trimmedCommand.startsWith("on ")) {
            return parseOnCommand(trimmedCommand);
        } else {
            throw new GalathException("I'm sorry, but I don't know what that means :-(");
        }
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
}