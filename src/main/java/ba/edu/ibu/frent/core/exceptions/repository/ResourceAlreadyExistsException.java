package ba.edu.ibu.frent.core.exceptions.repository;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a conflict, typically returned when a resource already exists
 * and a new creation or update operation cannot proceed without causing a conflict.
 *
 * @see GeneralException
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends GeneralException {

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with a specified message and default status code.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public ResourceAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with a default status code.
     */
    public ResourceAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value());
    }
}

