package com.bna.batches.security.filter;

import com.bna.batches.domain.beans.ProfilBatches;
import com.bna.batches.infrastructure.security.model.MenuResponse;
import com.bna.batches.security.jwt.JwtTokenUtil;
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
    private static final String DEFAULT_EMAIL  = "guest@bna.tn";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/authenticate",
            "/authenticate",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator",
            "/error"
    );

    @Value("${urlroles}")
    private String urlRoles;

    @Value("${codeApp}")
    private String codeApp;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractToken(request);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = extractUsername(jwtToken, response, request.getRequestURI());
        if (username == null) return; // response already written (expired token)

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        processAuthentication(request, response, filterChain, jwtToken, username);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(p -> uri.equals(p) || uri.startsWith(p));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private String extractUsername(String token, HttpServletResponse response, String uri) throws IOException {
        try {
            return jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException e) {
            log.warn("Unable to get JWT token");
            return null;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired for URI: {}", uri);
            SecurityContextHolder.clearContext();
            writeJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            return null;
        }
    }

    private void processAuthentication(HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain filterChain,
                                       String jwtToken,
                                       String username) throws ServletException, IOException {

        UserDetails userDetails = fetchUserDetailsFromRolesService(username);

        if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } else {
            log.warn("Token validation failed for user: {}", username);
            SecurityContextHolder.clearContext();
            writeJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    /** Calls the external roles service (same pattern as habil) */
    private UserDetails fetchUserDetailsFromRolesService(String username) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"username\":\"%s\",\"codeApp\":\"%s\"}", username, codeApp);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        MenuResponse menuResponse = restTemplate.postForObject(urlRoles, entity, MenuResponse.class);

        return buildUserDetails(username, menuResponse);
    }

    private UserDetails buildUserDetails(String username, MenuResponse menuResponse) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String email = DEFAULT_EMAIL;

        if (menuResponse != null && menuResponse.getMenuData() != null) {
            email = menuResponse.getMenuData().getMail() != null
                    ? menuResponse.getMenuData().getMail()
                    : DEFAULT_EMAIL;

            List<String> profils = new ArrayList<>();
            for (ProfilBatches profil : menuResponse.getMenuData().getProfils()) {
                profils.add(profil.getCodeProfil());
            }
            Collections.sort(profils);
            profils.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
        }

        return new User(username, email, authorities);
    }

    private void writeJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + message + "\",\"status\":" + status + "}");
    }
}
