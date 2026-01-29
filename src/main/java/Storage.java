import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath The relative path to the data file
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the data file.
     * Creates the file and directory if they don't exist.
     *
     * @return ArrayList of tasks loaded from file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            // Create directory and file if they don't exist
            createFileIfNotExists();

            // Read all lines from file
            List<String> lines = Files.readAllLines(filePath);

            // Parse each line into a task
            for (String line : lines) {
                try {
                    Task task = parseTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Skip corrupted lines
                    System.out.println("Warning: Skipping corrupted data: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Warning: Unable to load data file. Starting with empty task list.");
        }

        return tasks;
    }

    /**
     * Saves tasks to the data file.
     *
     * @param tasks The list of tasks to save
     * @throws IOException if unable to write to file
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        createFileIfNotExists();

        FileWriter writer = new FileWriter(filePath.toFile());
        for (Task task : tasks) {
            writer.write(convertTaskToLine(task) + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Creates the data file and its parent directory if they don't exist.
     *
     * @throws IOException if unable to create file or directory
     */
    private void createFileIfNotExists() throws IOException {
        // Create parent directory if it doesn't exist
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        // Create file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Parses a line from the file into a Task object.
     * Format: TaskType | isDone | description | [extra fields]
     *
     * @param line The line to parse
     * @return The parsed Task, or null if invalid
     */
    private Task parseTaskFromLine(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;  // Invalid format
        }

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        switch (taskType) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    task = new Deadline(description, parts[3]);
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    task = new Event(description, parts[3], parts[4]);
                }
                break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Converts a Task object to a line for saving to file.
     * Format: TaskType | isDone | description | [extra fields]
     *
     * @param task The task to convert
     * @return The formatted line
     */
    private String convertTaskToLine(Task task) {
        String isDone = task.getIsDone() ? "1" : "0";
        String line = "";

        if (task instanceof Todo) {
            line = "T | " + isDone + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            line = "D | " + isDone + " | " + task.getDescription() + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            line = "E | " + isDone + " | " + task.getDescription() + " | " + event.getFrom() + " | " + event.getTo();
        }

        return line;
    }
}