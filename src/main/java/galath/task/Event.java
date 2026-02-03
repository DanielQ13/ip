package galath.task;

import galath.exception.GalathException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task that starts at a specific time and ends at another time.
 * Example: "project meeting from Dec 2 2019, 2:00PM to Dec 2 2019, 4:00PM"
 *
 * Accepts date formats: "yyyy-MM-dd" or "yyyy-MM-dd HHmm"
 * Displays as: "MMM d yyyy" or "MMM d yyyy, h:mma"
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Creates a new Event task with date/time strings.
     *
     * @param description The description of the event
     * @param from The start date/time string (format: "yyyy-MM-dd" or "yyyy-MM-dd HHmm")
     * @param to The end date/time string (format: "yyyy-MM-dd" or "yyyy-MM-dd HHmm")
     * @throws GalathException if the date format is invalid
     */
    public Event(String description, String from, String to) throws GalathException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    /**
     * Creates a new Event task with LocalDateTime objects.
     *
     * @param description The description of the event
     * @param from The start time as a LocalDateTime object
     * @param to The end time as a LocalDateTime object
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
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
     * Returns the start date/time of the event.
     *
     * @return The start time as a LocalDateTime object
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date/time of the event.
     *
     * @return The end time as a LocalDateTime object
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the start date/time as a formatted string.
     * If time is midnight, shows date only.
     *
     * @return Formatted start date/time string
     */
    public String getFromString() {
        if (from.getHour() == 0 && from.getMinute() == 0) {
            return from.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return from.format(OUTPUT_FORMAT);
    }

    /**
     * Returns the end date/time as a formatted string.
     * If time is midnight, shows date only.
     *
     * @return Formatted end date/time string
     */
    public String getToString() {
        if (to.getHour() == 0 && to.getMinute() == 0) {
            return to.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return to.format(OUTPUT_FORMAT);
    }

    /**
     * Returns a string representation of the event task.
     * Format: [E][status_icon] description (from: start to: end)
     *
     * @return String representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromString() + " to: " + getToString() + ")";
    }
}