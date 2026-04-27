package com.bna.habil.infrastructure.security.jwt;


import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    // Constants
    private static final String CLAIM_KEY_ROLES = "roles";
    private static final String CLAIM_KEY_AUTHORITY = "authority";
    public static final long JWT_TOKEN_VALIDITY = 60L * 60 * 1000; // 1 hour

    private final Key key;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }


    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ROLES, userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date now = new Date(System.currentTimeMillis());
        final Date expiration = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Set<String> rolesFromToken = extractRolesFromToken(token);
        Set<String> rolesFromDb = extractRolesFromUserDetails(userDetails);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && rolesFromToken.equals(rolesFromDb);
    }

    private Set<String> extractRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Set<String> roles = new HashSet<>();

        Object rolesObject = claims.get(CLAIM_KEY_ROLES);

        if (rolesObject instanceof List<?> rolesList) {
            for (Object roleItem : rolesList) {
                if (roleItem instanceof Map<?, ?> roleMap) {
                    Object authority = roleMap.get(CLAIM_KEY_AUTHORITY);
                    if (authority instanceof String authorityStr) {
                        roles.add(authorityStr);
                    }
                }
            }
        }

        return roles;
    }

    private Set<String> extractRolesFromUserDetails(UserDetails userDetails) {
        Set<String> roles = new HashSet<>();

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        return roles;
    }
}
