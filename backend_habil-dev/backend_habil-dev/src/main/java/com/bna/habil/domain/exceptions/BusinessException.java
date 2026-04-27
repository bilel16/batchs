package com.bna.habil.domain.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base exception for all business logic exceptions
 */
public abstract class BusinessException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    protected BusinessException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    protected BusinessException(String message, Throwable cause, String errorCode, HttpStatus httpStatus) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}