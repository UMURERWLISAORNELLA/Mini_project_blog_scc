package com.example.blogmanagement.exception;

/**
 * Exception thrown when a request is invalid or cannot be processed due to
 * client error. Triggering this exception will result in a 400 Bad Request
 * response from the {@link com.example.blogmanagement.exception.GlobalExceptionHandler}.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}