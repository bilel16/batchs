package com.bna.habil.interfaces.controllers;


import java.nio.charset.StandardCharsets;
import java.util.*;

import com.bna.habil.domain.entities.extra.AuthData;
import com.bna.habil.domain.exceptions.AuthenticationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.bna.habil.domain.beans.ProfilHabil;
import com.bna.habil.infrastructure.security.jwt.JwtTokenUtil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.request.JwtRequest;
import com.bna.habil.interfaces.response.AuthResponse;
import com.bna.habil.interfaces.response.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationController {

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
            @Valid @RequestBody JwtRequest authenticationRequest) throws AuthenticationException {

        String clientIp = resolveClientIp(request);
        AuthResponse authResponse = authenticateUser(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword(),
                clientIp
        );

        JwtResponse jwtResponse = buildJwtResponse(authenticationRequest.getUsername(), authResponse);
        return ResponseEntity.ok(jwtResponse);

    }

    private String resolveClientIp(HttpServletRequest request) {
        return Constants.DEV.equals(envLocal) ? localIp : request.getRemoteAddr();
    }

    private AuthResponse authenticateUser(String username, String password, String ip) throws AuthenticationException {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, Constants.BASIC + encodedCredentials);

        com.bna.habil.domain.entities.extra.User user =
                new com.bna.habil.domain.entities.extra.User(ip, codeApp);

        HttpEntity<String> entity = new HttpEntity<>(user.toString(), headers);

        AuthResponse response = restTemplate.postForObject(urlAuth, entity, AuthResponse.class);

        log.info("Auth response: " + response.toString());

        if (response == null) {
            throw new AuthenticationException("Authentication service returned no response");
        }

        if (response.getReturnCode() != 0) {
            throw new AuthenticationException(response.getMessage());
        }

        return response;
    }

    private JwtResponse buildJwtResponse(String username, AuthResponse authResponse) {
        AuthData habilData = authResponse.getHabilData();

        List<SimpleGrantedAuthority> authorities = buildAuthorities(habilData.getProfils());
        String profilsJson = serializeProfilsToJson(habilData.getProfils());
        String email = Optional.ofNullable(habilData.getMail()).orElse("guest@bna.tn");

        UserDetails userDetails = new User(username, email, authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(
                token,
                username,
                profilsJson,
                habilData.getNom(),
                habilData.getPrenom(),
                habilData.getCodeStructure(),
                email,
                authResponse.getHabilData().getPoste(),
                authResponse.getHabilData().getCodePoste()
        );
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Collection<ProfilHabil> profils) {
        return profils.stream()
                .map(ProfilHabil::getCodeProfil)
                .filter(Objects::nonNull)
                .sorted()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    private String serializeProfilsToJson(Collection<ProfilHabil> profils) {
        try {
            return objectMapper.writeValueAsString(profils);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize profils to JSON", e);
            return "[]";
        }
    }
}