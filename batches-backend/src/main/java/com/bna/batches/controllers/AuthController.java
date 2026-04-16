package com.bna.batches.controllers;

import com.bna.batches.domain.beans.ProfilBatches;
import com.bna.batches.domain.entities.extra.AuthData;
import com.bna.batches.domain.entities.extra.AuthUser;
import com.bna.batches.domain.exceptions.AuthenticationException;
import com.bna.batches.infrastructure.utils.Constants;
import com.bna.batches.interfaces.request.LoginRequest;
import com.bna.batches.interfaces.response.AuthResponse;
import com.bna.batches.response.JwtResponse;
import com.bna.batches.security.jwt.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Value("${codeApp}")
    private String codeApp;

    @Value("${env.local}")
    private String envLocal;

    @Value("${local.ip}")
    private String localIp;

    @Value("${urlAuth}")
    private String urlAuth;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(
            HttpServletRequest request,
            @Valid @RequestBody LoginRequest loginRequest) {

        String clientIp = resolveClientIp(request);
        AuthResponse authResponse = authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                clientIp
        );

        JwtResponse jwtResponse = buildJwtResponse(loginRequest.getUsername(), authResponse);
        return ResponseEntity.ok(jwtResponse);
    }

    private String resolveClientIp(HttpServletRequest request) {
        return Constants.DEV.equals(envLocal) ? localIp : request.getRemoteAddr();
    }

    private AuthResponse authenticateUser(String username, String password, String ip) {
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, Constants.BASIC + encoded);

        AuthUser authUser = new AuthUser(ip, codeApp);
        HttpEntity<String> entity = new HttpEntity<>(authUser.toString(), headers);

        AuthResponse response = restTemplate.postForObject(urlAuth, entity, AuthResponse.class);

        log.info("Auth response: {}", response);

        if (response == null) {
            throw new AuthenticationException("Authentication service returned no response");
        }
        if (response.getReturnCode() != 0) {
            throw new AuthenticationException(response.getMessage());
        }

        return response;
    }

    private JwtResponse buildJwtResponse(String username, AuthResponse authResponse) {
        AuthData data = authResponse.getHabilData();

        List<SimpleGrantedAuthority> authorities = buildAuthorities(data.getProfils());
        String profilsJson = serializeToJson(data.getProfils());
        String email = Optional.ofNullable(data.getMail()).orElse("guest@bna.tn");

        UserDetails userDetails = new User(username, email, authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(
                token,
                username,
                profilsJson,
                data.getNom(),
                data.getPrenom(),
                data.getCodeStructure(),
                email,
                data.getPoste(),
                data.getCodePoste()
        );
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Collection<ProfilBatches> profils) {
        return profils.stream()
                .map(ProfilBatches::getCodeProfil)
                .filter(Objects::nonNull)
                .sorted()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    private String serializeToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize to JSON", e);
            return "[]";
        }
    }
}
