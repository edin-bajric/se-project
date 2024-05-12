package ba.edu.ibu.frent.core.exceptions.repository;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a resource not found, typically returned when a requested
 * resource is not present in the system.
 *
 * @see GeneralException
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends GeneralException {

    /**
     * Constructs a new {@code ResourceNotFoundException} with a specified message and default status code.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }

    /**
     * Constructs a new {@code ResourceNotFoundException} with a default status code.
     */
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}

