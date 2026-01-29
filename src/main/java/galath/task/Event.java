package galath.task;

import galath.task.Task;
import galath.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Event(String description, String from, String to) throws GalathException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

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

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getFromString() {
        if (from.getHour() == 0 && from.getMinute() == 0) {
            return from.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return from.format(OUTPUT_FORMAT);
    }

    public String getToString() {
        if (to.getHour() == 0 && to.getMinute() == 0) {
            return to.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return to.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromString() + " to: " + getToString() + ")";
    }
}