package ba.edu.ibu.frent.core.exceptions.auth;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception class representing a scenario where a user already exists, and the requested operation
 * conflicts with this existing user. It extends the GeneralException class and is annotated with
 * @ResponseStatus to indicate that the HTTP response status for this exception should be set to CONFLICT (409).
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends GeneralException {

    /**
     * Constructs a new UserAlreadyExistsException with a default status code (CONFLICT).
     */
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value());
    }

    /**
     * Constructs a new UserAlreadyExistsException with a custom message and the default status code (CONFLICT).
     *
     * @param message A custom error message describing the reason for the user already existing.
     */
    public UserAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }
}

