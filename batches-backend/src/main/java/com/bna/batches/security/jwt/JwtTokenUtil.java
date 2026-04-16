package com.bna.batches.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serial;
import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    private static final String CLAIM_KEY_ROLES = "roles";
    private static final String CLAIM_KEY_AUTHORITY = "authority";

    private final Key key;
    private final long expiration;

    public JwtTokenUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
        this.expiration = expiration;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ROLES, userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Set<String> rolesFromToken = extractRolesFromToken(token);
        Set<String> rolesFromUser = extractRolesFromUserDetails(userDetails);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && rolesFromToken.equals(rolesFromUser);
    }

    private Set<String> extractRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Set<String> roles = new HashSet<>();
        Object rolesObj = claims.get(CLAIM_KEY_ROLES);
        if (rolesObj instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof Map<?, ?> map) {
                    Object auth = map.get(CLAIM_KEY_AUTHORITY);
                    if (auth instanceof String authStr) roles.add(authStr);
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
