package com.example.blogmanagement.exception;

/**
 * Exception thrown when a requested entity is not found. Triggering this
 * exception will result in a 404 Not Found HTTP response from the
 * {@link com.example.blogmanagement.exception.GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}