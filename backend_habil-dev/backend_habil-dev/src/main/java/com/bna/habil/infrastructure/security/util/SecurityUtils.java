package com.bna.habil.infrastructure.security.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public final class SecurityUtils {
    private static final Set<String> ADMIN_ROLES = Set.of("SUPER_ADMIN_HABIL", "HABIL_RH");

    private SecurityUtils() {
        // Utility class
    }

    /**
     * Get the current authenticated user's matricule
     */
    public static String getCurrentUserStructure() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("authenticated user details : Principles :{} / {}",authentication.getDetails(), authentication);
            return authentication.getName();
        }
        return "SYSTEM";
    }

    /**
     * Get the current authenticated user's matricule
     */
    public static String getCurrentUserMatricule() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "SYSTEM";
    }
    /**
     * Get the current authenticated user's roles as a Set
     */
    public static Set<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        }
        return Collections.singleton("SYSTEM");
    }

    /**
     * Check if current user has any of the specified roles
     */
    public static boolean hasAnyRole(String... roles) {
        Set<String> userRoles = getCurrentUserRoles();
        return Arrays.stream(roles).anyMatch(userRoles::contains);
    }

    /**
     * Check if current user is an admin (SUPER_ADMIN_HABIL or HABIL_RH)
     */
    public static boolean isAdmin() {
        Set<String> userRoles = getCurrentUserRoles();
        return userRoles.stream().anyMatch(ADMIN_ROLES::contains);
    }
}