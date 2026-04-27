package com.bna.habil.infrastructure.security.handler;


import com.bna.habil.infrastructure.security.util.SecurityResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Handles authentication failures for requests that reach the entry point
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SecurityResponseWriter securityResponseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.debug("Authentication entry point triggered for URI: {}", request.getRequestURI());

        // Skip if response already committed (JWT filter handled it)
        if (response.isCommitted()) {
            log.debug("Response already committed, skipping entry point");
            return;
        }

        // Check if this is a forwarded error (404, 500, etc.)
        Integer errorStatusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (errorStatusCode != null) {
            // This is a forwarded error, let CustomErrorController handle it
            // But since we're here, the error controller wasn't reached
            handleForwardedError(request, response, errorStatusCode);
            return;
        }

        // Normal authentication failure
        String message = authException != null && authException.getMessage() != null
                ? authException.getMessage()
                : "Authentication required to access this resource";

        securityResponseWriter.writeUnauthorized(response, message, request.getRequestURI());
    }

    private void handleForwardedError(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Integer statusCode) throws IOException {

        HttpStatus status = HttpStatus.resolve(statusCode);
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        String originalUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String path = originalUri != null ? originalUri : request.getRequestURI();

        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String message = (errorMessage != null && !errorMessage.isEmpty())
                ? errorMessage
                : status.getReasonPhrase();

        String code = "ERR_" + status.value();

        log.debug("Handling forwarded error: status={}, path={}", status.value(), path);

        securityResponseWriter.writeError(response, status, code, message, path);
    }
}



