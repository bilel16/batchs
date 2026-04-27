package com.bna.habil.domain.exceptions.model;

import com.bna.habil.domain.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Exception for interim-related business rule violations
 */
public class InterimBusinessException extends BusinessException {

    private static final String DEFAULT_ERROR_CODE = "INTERIM_ERROR";

    public InterimBusinessException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST);
    }

    public InterimBusinessException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public InterimBusinessException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }

    public InterimBusinessException(String message, Throwable cause) {
        super(message, cause, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST);
    }
}