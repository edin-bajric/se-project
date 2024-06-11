package ba.edu.ibu.frent.core.exceptions.auth;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception class representing a scenario where access is forbidden.
 * It extends the GeneralException class and is annotated with @ResponseStatus to indicate
 * that the HTTP response status for this exception should be set to FORBIDDEN (403).
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoAccessException extends GeneralException {

    /**
     * Constructs a new NoAccessException with a default status code (FORBIDDEN).
     */
    public NoAccessException() {
        super(HttpStatus.FORBIDDEN.value());
    }

    /**
     * Constructs a new NoAccessException with a custom message and the default status code (FORBIDDEN).
     *
     * @param message A custom error message describing the reason for the access being forbidden.
     */
    public NoAccessException(String message) {
        super(HttpStatus.FORBIDDEN.value(), message);
    }
}

