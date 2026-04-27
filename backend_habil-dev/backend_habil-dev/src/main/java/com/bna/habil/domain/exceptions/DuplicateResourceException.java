package com.bna.habil.domain.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a resource that already exists
 */
public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message) {
        super(message, "DUPLICATE_RESOURCE", HttpStatus.CONFLICT);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause, "DUPLICATE_RESOURCE", HttpStatus.CONFLICT);
    }
}