package com.bna.habil.infrastructure.security.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Unified API error response following RFC 7807 Problem Details pattern
 * Single Responsibility: Represents error response structure
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private int status;
    private String error;
    private String message;
    private String path;
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    private List<FieldError> fieldErrors;
    private List<String> details;

    @Data
    @Builder
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;
    }

    // Factory methods for common error scenarios
    public static ApiError of(HttpStatus status, String message, String path) {
        return ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError unauthorized(String message, String path) {
        return ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code("AUTH_001")
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError forbidden(String message, String path) {
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .code("AUTH_002")
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError tokenExpired(String path) {
        return ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code("AUTH_003")
                .message("JWT token has expired")
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError invalidToken(String path) {
        return ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code("AUTH_004")
                .message("Invalid JWT token")
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiError notFound(String message, String path) {
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .code("RES_001")
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}