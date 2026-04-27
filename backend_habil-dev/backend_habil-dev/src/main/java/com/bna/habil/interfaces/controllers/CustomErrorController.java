package com.bna.habil.interfaces.controllers;


import com.bna.habil.infrastructure.security.model.ApiError;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Custom error controller to handle HTTP errors (404, 405, 500, etc.)
 * This prevents 404 errors from being converted to 401 by security filters
 */
@RestController
@Slf4j
public class CustomErrorController implements ErrorController {

    @PostMapping("/error")
    public ResponseEntity<ApiError> handleError(HttpServletRequest request) {

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        HttpStatus status = resolveStatus(statusCode);
        String message = resolveMessage(errorMessage, exception, status);
        String path = requestUri != null ? requestUri : request.getRequestURI();
        String code = resolveErrorCode(status);

        log.debug("Handling error: status={}, path={}, message={}", status.value(), path, message);

        ApiError error = ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    private HttpStatus resolveStatus(Integer statusCode) {
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpStatus status = HttpStatus.resolve(statusCode);
        return status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String resolveMessage(String errorMessage, Throwable exception, HttpStatus status) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            return errorMessage;
        }
        if (exception != null && exception.getMessage() != null) {
            return exception.getMessage();
        }
        return switch (status) {
            case NOT_FOUND -> "The requested resource was not found";
            case METHOD_NOT_ALLOWED -> "Request method not supported";
            case UNSUPPORTED_MEDIA_TYPE -> "Media type not supported";
            case BAD_REQUEST -> "Bad request";
            default -> status.getReasonPhrase();
        };
    }

    private String resolveErrorCode(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> "HTTP_404";
            case METHOD_NOT_ALLOWED -> "HTTP_405";
            case BAD_REQUEST -> "HTTP_400";
            case INTERNAL_SERVER_ERROR -> "HTTP_500";
            default -> "HTTP_" + status.value();
        };
    }
}
