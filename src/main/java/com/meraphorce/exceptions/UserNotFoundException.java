package com.meraphorce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user is not found in the database.
 * This results in a HTTP 404 Not Found response.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * @param message the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
