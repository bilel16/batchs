package com.bna.habil.controller;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.services.ProfilService;
import com.bna.habil.domain.entities.Profil;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.ProfilController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfilControllerTest {

    @Mock
    private ProfilService profilService;

    @InjectMocks
    private ProfilController profilController;

    private ProfilDto testProfilDto;

    @BeforeEach
    void setUp() {
        testProfilDto = createTestProfilDto("PROFIL001", "Test Profil", true);
    }

    // ==================== CREATE TESTS ====================

    @Test
    void createProfil_validDto_shouldReturnCreatedStatus() {
        // Arrange
        when(profilService.create(any(ProfilDto.class))).thenReturn(testProfilDto);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.createProfil(testProfilDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.CREATED, response.getBody().getMessage());
        assertEquals(testProfilDto, response.getBody().getData());

        verify(profilService, times(1)).create(testProfilDto);
    }

    @Test
    void createProfil_serviceThrowsException_shouldPropagateException() {
        // Arrange
        when(profilService.create(any(ProfilDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profilController.createProfil(testProfilDto));

        verify(profilService, times(1)).create(testProfilDto);
    }

    // ==================== READ TESTS ====================

    @Test
    void getAllProfils_shouldReturnAllProfils() {
        // Arrange
        List<ProfilDto> profils = Arrays.asList(
                createTestProfilDto("PROFIL001", "Profil 1", true),
                createTestProfilDto("PROFIL002", "Profil 2", false),
                createTestProfilDto("PROFIL003", "Profil 3", true)
        );
        when(profilService.findAll()).thenReturn(profils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getAllProfils();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(profils, response.getBody().getData());

        verify(profilService, times(1)).findAll();
    }

    @Test
    void getAllProfils_emptyList_shouldReturnEmptyList() {
        // Arrange
        when(profilService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getAllProfils();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(profilService, times(1)).findAll();
    }

    @Test
    void getProfilById_existingId_shouldReturnProfil() {
        // Arrange
        String codPflPfl = "PROFIL001";
        when(profilService.findById(codPflPfl)).thenReturn(testProfilDto);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getProfilById(codPflPfl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(testProfilDto, response.getBody().getData());

        verify(profilService, times(1)).findById(codPflPfl);
    }

    @Test
    void getProfilById_nonExistingId_shouldThrowException() {
        // Arrange
        String codPflPfl = "NONEXISTENT";
        when(profilService.findById(codPflPfl))
                .thenThrow(new RuntimeException("Profil not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profilController.getProfilById(codPflPfl));

        verify(profilService, times(1)).findById(codPflPfl);
    }

    // ==================== UPDATE TESTS ====================

    @Test
    void updateProfil_validDto_shouldReturnUpdatedProfil() {
        // Arrange
        String codPflPfl = "PROFIL001";
        ProfilDto updatedDto = createTestProfilDto(codPflPfl, "Updated Profil", true);
        when(profilService.update(eq(codPflPfl), any(ProfilDto.class))).thenReturn(updatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.updateProfil(codPflPfl, updatedDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.UPDATED, response.getBody().getMessage());
        assertEquals(updatedDto, response.getBody().getData());

        verify(profilService, times(1)).update(codPflPfl, updatedDto);
    }

    @Test
    void updateProfil_nonExistingId_shouldThrowException() {
        // Arrange
        String codPflPfl = "NONEXISTENT";
        when(profilService.update(eq(codPflPfl), any(ProfilDto.class)))
                .thenThrow(new RuntimeException("Profil not found"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> profilController.updateProfil(codPflPfl, testProfilDto));

        verify(profilService, times(1)).update(codPflPfl, testProfilDto);
    }

    // ==================== DELETE TESTS ====================

    @Test
    void deleteProfil_existingId_shouldReturnSuccessMessage() {
        // Arrange
        String codPflPfl = "PROFIL001";
        doNothing().when(profilService).delete(codPflPfl);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.deleteProfil(codPflPfl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals("Supprimé avec succès", response.getBody().getData());

        verify(profilService, times(1)).delete(codPflPfl);
    }

    @Test
    void deleteProfil_nonExistingId_shouldThrowException() {
        // Arrange
        String codPflPfl = "NONEXISTENT";
        doThrow(new RuntimeException("Profil not found")).when(profilService).delete(codPflPfl);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profilController.deleteProfil(codPflPfl));

        verify(profilService, times(1)).delete(codPflPfl);
    }

    // ==================== QUERY ENDPOINT TESTS ====================

    @Test
    void getProfilsByApplication_validApplication_shouldReturnProfils() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        List<Profil> profils = Arrays.asList(
                createTestProfilEntity("PROFIL001", "Profil 1", true),
                createTestProfilEntity("PROFIL002", "Profil 2", true)
        );
        when(profilService.getProfilByCodApp(codAppApp)).thenReturn(profils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getProfilsByApplication(codAppApp);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(profils, response.getBody().getData());

        verify(profilService, times(1)).getProfilByCodApp(codAppApp);
    }

    @Test
    void getManagerProfils_validApplication_shouldReturnManagerProfils() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        List<ProfilDto> managerProfils = Arrays.asList(
                createTestProfilDto("PROFIL001", "Manager Profil 1", true),
                createTestProfilDto("PROFIL002", "Manager Profil 2", true)
        );
        when(profilService.getManagerProfiles(codAppApp)).thenReturn(managerProfils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getManagerProfils(codAppApp);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(managerProfils, response.getBody().getData());

        verify(profilService, times(1)).getManagerProfiles(codAppApp);
    }

    @Test
    void getManagerProfils_serviceThrowsException_shouldPropagateException() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        when(profilService.getManagerProfiles(codAppApp))
                .thenThrow(new RuntimeException("Authorization error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profilController.getManagerProfils(codAppApp));

        verify(profilService, times(1)).getManagerProfiles(codAppApp);
    }

    @Test
    void getAvailableProfilsForUser_validUserAndApp_shouldReturnProfils() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        String targetUserMat = "USER001";
        List<ProfilDto> availableProfils = Arrays.asList(
                createTestProfilDto("PROFIL001", "Available Profil 1", true),
                createTestProfilDto("PROFIL002", "Available Profil 2", true)
        );
        when(profilService.getAvailableProfilesForUser(codAppApp, targetUserMat)).thenReturn(availableProfils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getAvailableProfilsForUser(codAppApp, targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(availableProfils, response.getBody().getData());

        verify(profilService, times(1)).getAvailableProfilesForUser(codAppApp, targetUserMat);
    }

    @Test
    void getAvailableProfilsForUser_userNotFound_shouldThrowException() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        String targetUserMat = "NONEXISTENT";
        when(profilService.getAvailableProfilesForUser(codAppApp, targetUserMat))
                .thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> profilController.getAvailableProfilsForUser(codAppApp, targetUserMat));

        verify(profilService, times(1)).getAvailableProfilesForUser(codAppApp, targetUserMat);
    }

    @Test
    void getNotAssignedProfilsForUser_validUserAndApp_shouldReturnProfils() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        String targetUserMat = "USER001";
        List<ProfilDto> notAssignedProfils = Arrays.asList(
                createTestProfilDto("PROFIL003", "Not Assigned Profil 1", true),
                createTestProfilDto("PROFIL004", "Not Assigned Profil 2", true)
        );
        when(profilService.getAvailableProfilesForUserNotAssgined(codAppApp, targetUserMat))
                .thenReturn(notAssignedProfils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getNotAssignedProfilsForUser(codAppApp, targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(notAssignedProfils, response.getBody().getData());

        verify(profilService, times(1)).getAvailableProfilesForUserNotAssgined(codAppApp, targetUserMat);
    }

    @Test
    void getNotAssignedProfilsForUser_emptyResult_shouldReturnEmptyList() throws Exception {
        // Arrange
        String codAppApp = "APP001";
        String targetUserMat = "USER001";
        when(profilService.getAvailableProfilesForUserNotAssgined(codAppApp, targetUserMat))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getNotAssignedProfilsForUser(codAppApp, targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(profilService, times(1)).getAvailableProfilesForUserNotAssgined(codAppApp, targetUserMat);
    }

    @Test
    void getProfilesByStructureId_validStructureId_shouldReturnProfils() {
        // Arrange
        Integer structureID = 1;
        List<ProfilDto> profils = Arrays.asList(
                createTestProfilDto("PROFIL001", "Profil 1", true),
                createTestProfilDto("PROFIL002", "Profil 2", true)
        );
        when(profilService.getProfilesByStructureId(structureID)).thenReturn(profils);

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getProfilesByStructureId(structureID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(profils, response.getBody().getData());

        verify(profilService, times(1)).getProfilesByStructureId(structureID);
    }

    @Test
    void getProfilesByStructureId_noProfilsFound_shouldReturnEmptyList() {
        // Arrange
        Integer structureID = 999;
        when(profilService.getProfilesByStructureId(structureID))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = profilController.getProfilesByStructureId(structureID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(profilService, times(1)).getProfilesByStructureId(structureID);
    }

    // ==================== HELPER METHODS ====================

    private ProfilDto createTestProfilDto(String code, String libelle, boolean actif) {
        ProfilDto dto = new ProfilDto();
        dto.setCodPflPfl(code);
        dto.setLibpflpfl(libelle);
        dto.setBoolEtatPfl(actif ? "1" : "0");
        return dto;
    }

    private Profil createTestProfilEntity(String code, String libelle, boolean actif) {
        Profil entity = new Profil();
        entity.setCodPflPfl(code);
        entity.setLibpflpfl(libelle);
        entity.setBoolEtatPfl(actif ? "1" : "0");
        return entity;
    }
}