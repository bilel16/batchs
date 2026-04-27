package com.bna.habil.domain.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation fails
 */
public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
    }
}