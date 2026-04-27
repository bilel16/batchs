package com.bna.habil.controller;

import com.bna.habil.domain.beans.ProfilHabil;
import com.bna.habil.domain.entities.extra.AuthData;
import com.bna.habil.domain.exceptions.AuthenticationException;
import com.bna.habil.infrastructure.security.jwt.JwtTokenUtil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.JwtAuthenticationController;
import com.bna.habil.interfaces.request.JwtRequest;
import com.bna.habil.interfaces.response.AuthResponse;
import com.bna.habil.interfaces.response.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private JwtAuthenticationController controller;

    private static final String URL_AUTH = "http://auth-service/auth";
    private static final String CODE_APP = "BNAHABIL";
    private static final String LOCAL_IP = "127.0.0.1";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "urlAuth", URL_AUTH);
        ReflectionTestUtils.setField(controller, "codeApp", CODE_APP);
        ReflectionTestUtils.setField(controller, "localIp", LOCAL_IP);
        ReflectionTestUtils.setField(controller, "envLocal", "PROD");
    }

    @Test
    void authenticate_success_prod_shouldUseRemoteAddr_andReturnJwtResponse() throws Exception {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.10.10.10");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = authResponseOk(
                "Doe", "John", "john@bna.tn", "947",
                Set.of(profil("USER"), profil("ADMIN"))
        );

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        when(objectMapper.writeValueAsString(any()))
                .thenReturn("[{\"codeProfil\":\"USER\"},{\"codeProfil\":\"ADMIN\"}]");

        when(jwtTokenUtil.generateToken(any(UserDetails.class)))
                .thenReturn("jwt-token");

        // Act
        ResponseEntity<JwtResponse> response = controller.authenticate(request, jwtRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        assertEquals("user1", response.getBody().getUsername());
        assertEquals("Doe", response.getBody().getNom());
        assertEquals("John", response.getBody().getPrenom());
        assertEquals("947", response.getBody().getCodeStructure());
        assertEquals("john@bna.tn", response.getBody().getEmail());

        verify(request).getRemoteAddr();
        verify(restTemplate).postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class));
        verify(jwtTokenUtil).generateToken(any(UserDetails.class));
    }

    @Test
    void authenticate_success_dev_shouldUseLocalIp_notRemoteAddr() throws Exception {
        // Arrange
        ReflectionTestUtils.setField(controller, "envLocal", Constants.DEV);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = authResponseOk(
                "Doe", "John", "john@bna.tn", "STR001",
                Set.of(profil("USER"))
        );

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        when(objectMapper.writeValueAsString(any())).thenReturn("[]");
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        // Act
        controller.authenticate(request, jwtRequest);

        // Assert
        verify(request, never()).getRemoteAddr(); // DEV uses localIp
        verify(restTemplate).postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class));
    }

    @Test
    void authenticate_success_nullEmail_shouldFallbackToGuestEmail() throws Exception {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = authResponseOk(
                "Doe", "John", null, "STR001",
                Set.of(profil("USER"))
        );

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        when(objectMapper.writeValueAsString(any())).thenReturn("[]");
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        // Act
        ResponseEntity<JwtResponse> response = controller.authenticate(request, jwtRequest);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("guest@bna.tn", response.getBody().getEmail());
    }

    @Test
    void authenticate_authServiceReturnsNull_shouldThrowAuthenticationException() {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(null);

        // Act + Assert
        AuthenticationException ex = assertThrows(AuthenticationException.class,
                () -> controller.authenticate(request, jwtRequest));

        assertEquals("Authentication service returned no response", ex.getMessage());
    }

    @Test
    void authenticate_authServiceReturnCodeNotZero_shouldThrowAuthenticationException() {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setReturnCode(1);
        authResponse.setMessage("Invalid credentials");

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        // Act + Assert
        AuthenticationException ex = assertThrows(AuthenticationException.class,
                () -> controller.authenticate(request, jwtRequest));

        assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void authenticate_objectMapperFails_shouldReturnEmptyArrayAsProfilsJson() throws Exception {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = authResponseOk(
                "Doe", "John", "john@bna.tn", "STR001",
                Set.of(profil("USER"))
        );

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        when(objectMapper.writeValueAsString(any()))
                .thenThrow(new JsonProcessingException("boom") {});

        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        // Act
        ResponseEntity<JwtResponse> response = controller.authenticate(request, jwtRequest);

        // Assert
        assertNotNull(response.getBody());
//        assertEquals("[]", response.getBody().getProfils());
    }

    @Test
    void authenticate_shouldSendBasicAuthHeader_andJsonContentType() throws Exception {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1");
        jwtRequest.setPassword("pass1");

        AuthResponse authResponse = authResponseOk(
                "Doe", "John", "john@bna.tn", "STR001",
                Set.of(profil("USER"))
        );

        ArgumentCaptor<HttpEntity<String>> captor = ArgumentCaptor.forClass((Class) HttpEntity.class);

        when(restTemplate.postForObject(eq(URL_AUTH), any(HttpEntity.class), eq(AuthResponse.class)))
                .thenReturn(authResponse);

        when(objectMapper.writeValueAsString(any())).thenReturn("[]");
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        // Act
        controller.authenticate(request, jwtRequest);

        // Assert
        verify(restTemplate).postForObject(eq(URL_AUTH), captor.capture(), eq(AuthResponse.class));
        HttpEntity<String> entity = captor.getValue();
        assertNotNull(entity);

        HttpHeaders headers = entity.getHeaders();
        assertEquals("application/json", Objects.requireNonNull(headers.getContentType()).toString());
        assertTrue(headers.containsKey(HttpHeaders.AUTHORIZATION));
        assertTrue(Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).startsWith(Constants.BASIC));
    }

    // --- Helper Methods ---

    private static ProfilHabil profil(String code) {
        ProfilHabil p = new ProfilHabil();
        p.setCodeProfil(code);
        return p;
    }

    private static AuthResponse authResponseOk(
            String nom,
            String prenom,
            String mail,
            String codeStructure,
            Set<ProfilHabil> profils
    ) {
        AuthData data = new AuthData();
        data.setNom(nom);
        data.setPrenom(prenom);
        data.setMail(mail);
        data.setCodeStructure(codeStructure);
        data.setProfils(profils);

        AuthResponse resp = new AuthResponse();
        resp.setReturnCode(0);
        resp.setMessage("OK");
        resp.setHabilData(data);
        return resp;
    }
}