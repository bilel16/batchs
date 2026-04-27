package com.bna.habil.infrastructure.security.handler;

import com.bna.habil.infrastructure.security.util.SecurityResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * Handles 403 Forbidden scenarios when authenticated user lacks permissions
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final SecurityResponseWriter securityResponseWriter;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "anonymous";

        log.warn("Access denied for user '{}' to URI: {}", username, request.getRequestURI());

        if (response.isCommitted()) {
            log.debug("Response already committed");
            return;
        }

        String message = accessDeniedException.getMessage() != null
                ? accessDeniedException.getMessage()
                : "You don't have permission to access this resource";

        securityResponseWriter.writeForbidden(response, message, request.getRequestURI());
    }
}