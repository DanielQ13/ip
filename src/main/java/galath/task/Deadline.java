package galath.task;

import galath.exception.GalathException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that needs to be done before a specific date/time.
 * Example: "submit report by Dec 2 2019, 6:00PM"
 *
 * Accepts date formats: "yyyy-MM-dd" or "yyyy-MM-dd HHmm"
 * Displays as: "MMM d yyyy" or "MMM d yyyy, h:mma"
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Creates a new Deadline task with a date/time string.
     *
     * @param description The description of the deadline task
     * @param by The deadline date/time string (format: "yyyy-MM-dd" or "yyyy-MM-dd HHmm")
     * @throws GalathException if the date format is invalid
     */
    public Deadline(String description, String by) throws GalathException {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Creates a new Deadline task with a LocalDateTime object.
     *
     * @param description The description of the deadline task
     * @param by The deadline as a LocalDateTime object
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date/time string into LocalDateTime.
     * Accepts formats: "yyyy-MM-dd HHmm" or "yyyy-MM-dd"
     *
     * @param dateTimeStr The date/time string to parse
     * @return LocalDateTime object
     * @throws GalathException if the format is invalid
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws GalathException {
        try {
            // Try parsing with time first
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT);
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing date only (defaults to midnight)
                return LocalDateTime.parse(dateTimeStr + " 0000", INPUT_FORMAT);
            } catch (DateTimeParseException e2) {
                throw new GalathException("Invalid date format. Please use: yyyy-MM-dd or yyyy-MM-dd HHmm\n     Example: 2019-12-02 or 2019-12-02 1800");
            }
        }
    }

    /**
     * Returns the deadline date/time.
     *
     * @return The deadline as a LocalDateTime object
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns the deadline date/time as a formatted string.
     * If time is midnight, shows date only.
     *
     * @return Formatted date/time string
     */
    public String getByString() {
        // If time is midnight, show date only
        if (by.getHour() == 0 && by.getMinute() == 0) {
            return by.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return by.format(OUTPUT_FORMAT);
    }

    /**
     * Returns a string representation of the deadline task.
     * Format: [D][status_icon] description (by: date)
     *
     * @return String representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByString() + ")";
    }
}