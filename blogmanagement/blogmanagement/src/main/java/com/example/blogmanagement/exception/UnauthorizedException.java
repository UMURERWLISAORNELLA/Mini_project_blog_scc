package com.example.blogmanagement.exception;

/**
 * Exception thrown when a user attempts to access or modify a resource
 * they are not authorized to access.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Construct a new UnauthorizedException with the specified message.
     *
     * @param message the detail message
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Construct a new UnauthorizedException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
