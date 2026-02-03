package galath.task;

/**
 * Represents a todo task without any date/time attached.
 * Example: "borrow book", "visit theme park"
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     * Format: [T][status_icon] description
     *
     * @return String representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
