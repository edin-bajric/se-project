package ba.edu.ibu.frent.core.exceptions.general;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a bad request, typically returned when the client sends a malformed
 * or invalid request to the server.
 *
 * @see GeneralException
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends GeneralException {

    /**
     * Constructs a new {@code BadRequestException} with a default status code.
     */
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Constructs a new {@code BadRequestException} with a specified message and status code.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}

