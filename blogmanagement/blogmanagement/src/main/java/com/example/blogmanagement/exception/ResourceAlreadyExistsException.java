package com.example.blogmanagement.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * Triggering this exception results in a 409 Conflict HTTP response.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}