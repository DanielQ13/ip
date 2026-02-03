package galath.exception;

/**
 * Represents exceptions specific to the Galath application.
 * Used for handling errors such as invalid commands, invalid task numbers,
 * and invalid date formats.
 */
public class GalathException extends Exception {

    /**
     * Creates a new GalathException with the specified error message.
     *
     * @param message The error message describing what went wrong
     */
    public GalathException(String message) {
        super(message);
    }
}
