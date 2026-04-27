package com.bna.habil.infrastructure.security.filter;

import com.bna.habil.domain.beans.ProfilHabil;
import com.bna.habil.infrastructure.security.model.MenuResponse;
import com.bna.habil.infrastructure.security.jwt.JwtTokenUtil;
import com.bna.habil.infrastructure.security.util.SecurityResponseWriter;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String DEFAULT_EMAIL = "guest@bna.tn";
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/authenticate",
            "/authenticate",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator",
            "/error"  // Important: exclude /error to prevent 404 -> 401
    );

    @Value("${urlroles}")
    private String urlroles;

    @Value("${codeApp}")
    private String codeApp;

    private final JwtTokenUtil jwtTokenUtil;
    private final SecurityResponseWriter securityResponseWriter;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, SecurityResponseWriter securityResponseWriter) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.securityResponseWriter = securityResponseWriter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.debug("Processing JWT filter for: {}", request.getRequestURI());

        if (shouldSkipFilter(request)) {
            log.debug("Skipping JWT filter for excluded endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractToken(request);

        if (jwtToken == null) {
            log.warn("Request does not contain Bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        String username = extractUsername(jwtToken, request, response);

        if (username == null) {
            // Response already written by extractUsername if token expired
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        processAuthentication(request, response, filterChain, jwtToken, username);
    }

        private boolean shouldSkipFilter(HttpServletRequest request) {
            String requestURI = request.getRequestURI();
            return EXCLUDED_PATHS.stream()
                    .anyMatch(path -> requestURI.equals(path) || requestURI.startsWith(path));
        }

    private String extractToken(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
            log.debug("Bearer token found in request");
            return requestTokenHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private String extractUsername(String jwtToken, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        try {
            return jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            log.info("Unable to get JWT Token");
            return null;
        } catch (ExpiredJwtException e) {
            log.info("JWT Token has expired");
            SecurityContextHolder.clearContext();
            securityResponseWriter.writeTokenExpired(response, request.getRequestURI());
            return null;
        }
    }

    private void processAuthentication(HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain filterChain,
                                       String jwtToken,
                                       String username) throws ServletException, IOException {

        log.debug("Processing token validation");

        UserDetails userDetails = fetchUserDetails(username);

        Boolean tokenValid=jwtTokenUtil.validateToken(jwtToken, userDetails);
        if (Boolean.TRUE.equals(tokenValid)) {
            log.debug("Token is valid, setting authentication");
            setAuthentication(request, userDetails);
            filterChain.doFilter(request, response);
        } else {
            log.warn("Token validation failed");
            SecurityContextHolder.clearContext();
            securityResponseWriter.writeInvalidToken(response, request.getRequestURI());
        }
    }

    // ========== YOUR EXISTING BUSINESS LOGIC - UNCHANGED ==========

    private UserDetails fetchUserDetails(String username) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = buildRolesRequestBody(username);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        MenuResponse menuResponse = restTemplate.postForObject(urlroles, entity, MenuResponse.class);

        return buildUserDetails(username, menuResponse);
    }

    private String buildRolesRequestBody(String username) {
        return String.format("{\"username\":\"%s\",\"codeApp\":\"%s\"}", username, codeApp);
    }

    private UserDetails buildUserDetails(String username, MenuResponse menuResponse) {
        List<SimpleGrantedAuthority> authorities = extractAuthorities(menuResponse);
        String email = extractEmail(menuResponse);

        return new User(username, email, authorities);
    }

    private List<SimpleGrantedAuthority> extractAuthorities(MenuResponse menuResponse) {
        List<String> profils = new ArrayList<>();

        for (ProfilHabil profil : menuResponse.getMenuData().getProfils()) {
            profils.add(profil.getCodeProfil());
        }

        Collections.sort(profils);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String profil : profils) {
            authorities.add(new SimpleGrantedAuthority(profil));
        }

        return authorities;
    }

    private String extractEmail(MenuResponse menuResponse) {
        String mail = menuResponse.getMenuData().getMail();
        return mail != null ? mail : DEFAULT_EMAIL;
    }

    // ========== END YOUR EXISTING BUSINESS LOGIC ==========

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}