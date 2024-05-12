package ba.edu.ibu.frent.core.exceptions.auth;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception class representing a scenario where access is unauthorized.
 * It extends the GeneralException class and is annotated with @ResponseStatus to indicate
 * that the HTTP response status for this exception should be set to UNAUTHORIZED (401).
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends GeneralException {

    /**
     * Constructs a new UnauthorizedException with a default status code (UNAUTHORIZED).
     */
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Constructs a new UnauthorizedException with a custom message and the default status code (UNAUTHORIZED).
     *
     * @param message A custom error message describing the reason for the unauthorized access.
     */
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
    }
}

