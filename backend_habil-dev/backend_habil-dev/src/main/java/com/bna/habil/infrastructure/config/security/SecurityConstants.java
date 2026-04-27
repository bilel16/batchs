package com.bna.habil.infrastructure.config.security;


import java.util.List;

/**
 * Centralized security constants
 * Open/Closed Principle: Add new paths without modifying existing code
 */
public final class SecurityConstants {

    private SecurityConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Public endpoints that don't require authentication
    public static final List<String> PUBLIC_URLS = List.of(
            "/api/authenticate",
            "/api/hr/**",
            "/authenticate",
            "/api/v1/habilitation/roles",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/error",
            "/api/ldap/**"
    );

    // Paths that the JWT filter should skip
    public static final List<String> FILTER_SKIP_PATHS = List.of(
            "/api/authenticate",
            "/authenticate",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator",
            "/error"
    );

    public static String[] getPublicUrlsArray() {
        return PUBLIC_URLS.toArray(new String[0]);
    }
}
