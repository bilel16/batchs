package com.bna.habil.infrastructure.config;

import com.bna.habil.domain.exceptions.AuthenticationException;
import com.bna.habil.domain.exceptions.BatchOperationException;
import com.bna.habil.domain.exceptions.BusinessException;
import com.bna.habil.infrastructure.security.model.ApiError;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Global exception handler for controller-level exceptions
 * Security exceptions are handled by security layer, this is a fallback
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle your existing BusinessException
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        log.error("Business exception: {}", ex.getMessage(), ex);

        ApiError error = ApiError.builder()
                .status(ex.getHttpStatus().value())
                .error(ex.getHttpStatus().getReasonPhrase())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    /**
     * Handle your existing BatchOperationException
     * Preserves your existing BatchError structure
     */
    @ExceptionHandler(BatchOperationException.class)
    public ResponseEntity<BatchErrorResponse> handleBatchOperationException(
            BatchOperationException ex, HttpServletRequest request) {

        log.error("Batch operation exception: {}", ex.getMessage(), ex);

        BatchErrorResponse error = BatchErrorResponse.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .errors(ex.getErrors())  // Your existing BatchError list
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(error);
    }

    /**
     * Handle TransactionSystemException which WRAPS ConstraintViolationException
     * when validation fails during Hibernate flush at commit time.
     * Chain: TransactionSystemException → RollbackException → ConstraintViolationException
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiError> handleTransactionException(
            @Nonnull TransactionSystemException ex, HttpServletRequest request) {

        // Walk the full cause chain to find ConstraintViolationException
        Throwable cause = ex;
        while (cause != null) {
            if (cause instanceof jakarta.validation.ConstraintViolationException cve) {
                return handleConstraintViolation(cve, request);
            }
            cause = cause.getCause();
        }

        // Not a validation issue
        log.error("Transaction error: {}", ex.getMessage(), ex);

        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code("TRANSACTION_ERROR")
                .message("An unexpected transaction error occurred")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handle validation exceptions
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .toList();

        String path = extractPath(request);

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code("VALIDATION_ERROR")
                .message("Validation failed")
                .fieldErrors(fieldErrors)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handle constraint violations
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> ApiError.FieldError.builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .rejectedValue(violation.getInvalidValue())
                        .build())
                .toList();

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code("CONSTRAINT_VIOLATION")
                .message("Constraint violation")
                .fieldErrors(fieldErrors)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Fallback handler for AuthenticationException that might bubble up to controller
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {

        log.warn("Authentication exception reached controller layer: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code("AUTHENTICATION_ERROR")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Fallback handler for AccessDeniedException that might bubble up to controller
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        log.warn("Access denied exception reached controller layer: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .code("ACCESS_DENIED")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    /**
     * Handle all other unexpected exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected exception: {}", ex.getMessage(), ex);

        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ApiError.FieldError mapFieldError(org.springframework.validation.FieldError fieldError) {
        return ApiError.FieldError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .rejectedValue(fieldError.getRejectedValue())
                .build();
    }

    private String extractPath(WebRequest request) {
        if (request instanceof ServletWebRequest servletRequest) {
            return servletRequest.getRequest().getRequestURI();
        }
        return "";
    }

    /**
     * Response class for batch operations - matches your existing structure
     */
    @lombok.Data
    @lombok.Builder
    public static class BatchErrorResponse {
        private String code;
        private String message;
        private List<BatchOperationException.BatchError> errors;
        private String path;
        private LocalDateTime timestamp;
    }
}
