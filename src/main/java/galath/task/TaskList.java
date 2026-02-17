package galath.task;

import java.time.LocalDate;
import java.util.ArrayList;

import galath.exception.GalathException;

/**
 * Manages the list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "Tasks list should be initialized";
        assert tasks.isEmpty() : "New empty TaskList should have no tasks";
    }

    /**
     * Creates a TaskList with existing tasks.
     *
     * @param tasks The list of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list passed to TaskList should not be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add a null task to the list";
        int sizeBefore = tasks.size();
        tasks.add(task);
        assert tasks.size() == sizeBefore + 1 : "Task list size should increase by 1 after adding";
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete (0-based)
     * @return The deleted task
     * @throws GalathException if the index is invalid
     */
    public Task deleteTask(int index) throws GalathException {
        assert index >= 0 : "Task index should not be negative";
        if (index < 0 || index >= tasks.size()) {
            throw new GalathException("Task number " + (index + 1) + " does not exist. You have " + tasks.size() + " task(s) in your list.");
        }
        int sizeBefore = tasks.size();
        Task removed = tasks.remove(index);
        assert removed != null : "Removed task should not be null";
        assert tasks.size() == sizeBefore - 1 : "Task list size should decrease by 1 after deletion";
        return removed;
    }

    /**
     * Gets a task from the list.
     *
     * @param index The index of the task (0-based)
     * @return The task at the specified index
     * @throws GalathException if the index is invalid
     */
    public Task getTask(int index) throws GalathException {
        assert index >= 0 : "Task index should not be negative";
        if (index < 0 || index >= tasks.size()) {
            throw new GalathException("Task number " + (index + 1) + " does not exist. You have " + tasks.size() + " task(s) in your list.");
        }
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";
        return task;
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return The number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets all tasks in the list.
     *
     * @return The ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets tasks occurring on a specific date.
     *
     * @param date The date to search for
     * @return ArrayList of tasks on that date
     */
    public ArrayList<Task> getTasksOnDate(LocalDate date) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().toLocalDate().equals(date)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDate eventStart = event.getFrom().toLocalDate();
                LocalDate eventEnd = event.getTo().toLocalDate();
                if (!date.isBefore(eventStart) && !date.isAfter(eventEnd)) {
                    matchingTasks.add(task);
                }
            }
        }

        return matchingTasks;
    }

    /**
     * Finds tasks containing the specified keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for
     * @return ArrayList of tasks matching the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }

        return matchingTasks;
    }
}