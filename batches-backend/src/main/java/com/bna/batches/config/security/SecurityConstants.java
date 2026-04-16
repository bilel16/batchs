package com.bna.batches.config.security;

import java.util.List;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final List<String> PUBLIC_URLS = List.of(
            "/authenticate",
            "/api/authenticate",
            "/h2-console/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/**",
            "/error"
    );

    public static String[] getPublicUrlsArray() {
        return PUBLIC_URLS.toArray(new String[0]);
    }
}
