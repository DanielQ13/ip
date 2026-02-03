package galath.task;

import galath.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String by) throws GalathException {
        super(description);
        this.by = parseDateTime(by);
    }

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

    public LocalDateTime getBy() {
        return by;
    }

    public String getByString() {
        // If time is midnight, show date only
        if (by.getHour() == 0 && by.getMinute() == 0) {
            return by.format(OUTPUT_FORMAT_DATE_ONLY);
        }
        return by.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByString() + ")";
    }
}