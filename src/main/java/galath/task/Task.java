package galath.task;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        assert description != null : "Task description should not be null";
        assert !description.trim().isEmpty() : "Task description should not be empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, " " otherwise
     */
    public String getStatusIcon() {
        String icon = isDone ? "X" : " ";
        assert icon.equals("X") || icon.equals(" ") : "Status icon must be X or space";
        return icon;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task.
     * Format: [status_icon] description
     *
     * @return String representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}