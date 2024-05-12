package ba.edu.ibu.frent.core.exceptions.auth;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception class representing a scenario where invalid parameters are sent in a request.
 * It extends the GeneralException class and is annotated with @ResponseStatus to indicate
 * that the HTTP response status for this exception should be set to BAD_REQUEST (400).
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParametersSentException extends GeneralException {

    /**
     * Constructs a new InvalidParametersSentException with a default status code (BAD_REQUEST).
     */
    public InvalidParametersSentException() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Constructs a new InvalidParametersSentException with a custom message and the default status code (BAD_REQUEST).
     *
     * @param message A custom error message describing the nature of the invalid parameters.
     */
    public InvalidParametersSentException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}

