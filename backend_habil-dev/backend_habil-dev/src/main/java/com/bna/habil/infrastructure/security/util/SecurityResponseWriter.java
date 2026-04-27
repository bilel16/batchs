package com.bna.habil.infrastructure.security.util;


import com.bna.habil.infrastructure.security.model.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Utility class for writing security-related error responses
 * Single Responsibility: Only handles writing error responses
 * Dependency Inversion: Depends on abstraction (ObjectMapper)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityResponseWriter {

    private final ObjectMapper objectMapper;

    public void writeError(HttpServletResponse response, HttpStatus status,
                           String code, String message, String path) throws IOException {

        if (response.isCommitted()) {
            log.debug("Response already committed, skipping write");
            return;
        }

        ApiError error = ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(response.getOutputStream(), error);
    }

    public void writeUnauthorized(HttpServletResponse response, String message, String path)
            throws IOException {
        writeError(response, HttpStatus.UNAUTHORIZED, "AUTH_001", message, path);
    }

    public void writeForbidden(HttpServletResponse response, String message, String path)
            throws IOException {
        writeError(response, HttpStatus.FORBIDDEN, "AUTH_002", message, path);
    }

    public void writeTokenExpired(HttpServletResponse response, String path) throws IOException {
        writeError(response, HttpStatus.UNAUTHORIZED, "AUTH_003", "JWT Token has expired", path);
    }

    public void writeInvalidToken(HttpServletResponse response, String path) throws IOException {
        writeError(response, HttpStatus.UNAUTHORIZED, "AUTH_004", "Invalid JWT token", path);
    }
}
