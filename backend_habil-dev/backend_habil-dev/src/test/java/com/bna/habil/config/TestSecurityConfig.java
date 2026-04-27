package com.bna.habil.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@EnableMethodSecurity
public class TestSecurityConfig {

    @Bean
    SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/applications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/applications/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                        (req, res, exAuth) ->
                                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                                                )
                );;
        return http.build();
    }
}

