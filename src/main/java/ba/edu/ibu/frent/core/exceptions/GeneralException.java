package ba.edu.ibu.frent.core.exceptions;

/**
 * A custom runtime exception class representing a general exception in the application.
 * This class extends {@code RuntimeException} and allows specifying an HTTP status code.
 */
public class GeneralException extends RuntimeException {

    /**
     * The HTTP status code associated with the exception (default is 500 - Internal Server Error).
     */
    private int httpCode = 500;

    /**
     * Constructs a new {@code GeneralException} with a specified HTTP status code.
     *
     * @param httpCode The HTTP status code associated with the exception.
     */
    public GeneralException(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * Constructs a new {@code GeneralException} with a specified HTTP status code and message.
     *
     * @param httpCode The HTTP status code associated with the exception.
     * @param message  The detail message describing the reason for the exception.
     */
    public GeneralException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    /**
     * Constructs a new {@code GeneralException} with a specified message and a cause.
     *
     * @param message The detail message describing the reason for the exception.
     * @param e       The cause of the exception.
     */
    public GeneralException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Constructs a new {@code GeneralException} with a cause.
     *
     * @param e The cause of the exception.
     */
    public GeneralException(Exception e) {
        super(e);
    }

    /**
     * Constructs a new {@code GeneralException} with a specified message.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public GeneralException(String message) {
        super(message);
    }
}

