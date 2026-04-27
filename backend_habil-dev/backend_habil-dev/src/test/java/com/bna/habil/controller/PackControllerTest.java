package com.bna.habil.controller;

import com.bna.habil.application.dto.PackDto;
import com.bna.habil.application.services.PackService;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.PackController;
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
class PackControllerTest {

    @Mock
    private PackService packService;

    @InjectMocks
    private PackController packController;

    private PackDto testPackDto;

    @BeforeEach
    void setUp() {
        testPackDto = createTestPackDto("PACK001", "Test Pack", true);
    }

    // ==================== CREATE TESTS ====================

    @Test
    void createPack_validDto_shouldReturnCreatedStatus() {
        // Arrange
        when(packService.create(any(PackDto.class))).thenReturn(testPackDto);

        // Act
        ResponseEntity<ResponseHabil> response = packController.createPack(testPackDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.CREATED, response.getBody().getMessage());
        assertEquals(testPackDto, response.getBody().getData());

        verify(packService, times(1)).create(testPackDto);
    }

    @Test
    void createPack_serviceThrowsException_shouldPropagateException() {
        // Arrange
        when(packService.create(any(PackDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> packController.createPack(testPackDto));

        verify(packService, times(1)).create(testPackDto);
    }

    // ==================== READ TESTS ====================

    @Test
    void getAllPacks_shouldReturnAllPacks() {
        // Arrange
        List<PackDto> packs = Arrays.asList(
                createTestPackDto("PACK001", "Pack 1", true),
                createTestPackDto("PACK002", "Pack 2", false),
                createTestPackDto("PACK003", "Pack 3", true)
        );
        when(packService.findAll()).thenReturn(packs);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getAllPacks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(packs, response.getBody().getData());

        verify(packService, times(1)).findAll();
    }

    @Test
    void getAllPacks_emptyList_shouldReturnEmptyList() {
        // Arrange
        when(packService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = packController.getAllPacks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(packService, times(1)).findAll();
    }

    @Test
    void getPackById_existingId_shouldReturnPack() {
        // Arrange
        String codPackPack = "PACK001";
        when(packService.findById(codPackPack)).thenReturn(testPackDto);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getPackById(codPackPack);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(testPackDto, response.getBody().getData());

        verify(packService, times(1)).findById(codPackPack);
    }

    @Test
    void getPackById_nonExistingId_shouldThrowException() {
        // Arrange
        String codPackPack = "NONEXISTENT";
        when(packService.findById(codPackPack))
                .thenThrow(new RuntimeException("Pack not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> packController.getPackById(codPackPack));

        verify(packService, times(1)).findById(codPackPack);
    }

    // ==================== UPDATE TESTS ====================

    @Test
    void updatePack_validDto_shouldReturnUpdatedPack() {
        // Arrange
        String codPackPack = "PACK001";
        PackDto updatedDto = createTestPackDto(codPackPack, "Updated Pack", true);
        when(packService.update(eq(codPackPack), any(PackDto.class))).thenReturn(updatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = packController.updatePack(codPackPack, updatedDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.UPDATED, response.getBody().getMessage());
        assertEquals(updatedDto, response.getBody().getData());

        verify(packService, times(1)).update(codPackPack, updatedDto);
    }

    @Test
    void updatePack_nonExistingId_shouldThrowException() {
        // Arrange
        String codPackPack = "NONEXISTENT";
        when(packService.update(eq(codPackPack), any(PackDto.class)))
                .thenThrow(new RuntimeException("Pack not found"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> packController.updatePack(codPackPack, testPackDto));

        verify(packService, times(1)).update(codPackPack, testPackDto);
    }

    // ==================== DELETE TESTS ====================

    @Test
    void deletePack_existingId_shouldReturnSuccessMessage() {
        // Arrange
        String codPackPack = "PACK001";
        doNothing().when(packService).delete(codPackPack);

        // Act
        ResponseEntity<ResponseHabil> response = packController.deletePack(codPackPack);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals("Supprimé avec succès", response.getBody().getData());

        verify(packService, times(1)).delete(codPackPack);
    }

    @Test
    void deletePack_nonExistingId_shouldThrowException() {
        // Arrange
        String codPackPack = "NONEXISTENT";
        doThrow(new RuntimeException("Pack not found")).when(packService).delete(codPackPack);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> packController.deletePack(codPackPack));

        verify(packService, times(1)).delete(codPackPack);
    }

    // ==================== QUERY ENDPOINT TESTS ====================

    @Test
    void getActivePacks_shouldReturnOnlyActivePacks() {
        // Arrange
        List<PackDto> activePacks = Arrays.asList(
                createTestPackDto("PACK001", "Active Pack 1", true),
                createTestPackDto("PACK002", "Active Pack 2", true)
        );
        when(packService.getActivePacks()).thenReturn(activePacks);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getActivePacks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(activePacks, response.getBody().getData());

        verify(packService, times(1)).getActivePacks();
    }

    @Test
    void getPacksByNiveau_validNiveau_shouldReturnPacks() {
        // Arrange
        String codNivhPfl = "NIV001";
        List<PackDto> packs = Arrays.asList(
                createTestPackDto("PACK001", "Pack 1", true),
                createTestPackDto("PACK002", "Pack 2", true)
        );
        when(packService.getPacksByNiveauHierarchique(codNivhPfl)).thenReturn(packs);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getPacksByNiveau(codNivhPfl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(packs, response.getBody().getData());

        verify(packService, times(1)).getPacksByNiveauHierarchique(codNivhPfl);
    }

    @Test
    void getPacksByNiveau_noPacksFound_shouldReturnEmptyList() {
        // Arrange
        String codNivhPfl = "NIV999";
        when(packService.getPacksByNiveauHierarchique(codNivhPfl))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = packController.getPacksByNiveau(codNivhPfl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(packService, times(1)).getPacksByNiveauHierarchique(codNivhPfl);
    }

    @Test
    void getPacksByCategorie_validCategorie_shouldReturnPacks() {
        // Arrange
        String codCatpPfl = "CAT001";
        List<PackDto> packs = Arrays.asList(
                createTestPackDto("PACK001", "Pack 1", true),
                createTestPackDto("PACK002", "Pack 2", true)
        );
        when(packService.getPacksByCategorie(codCatpPfl)).thenReturn(packs);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getPacksByCategorie(codCatpPfl);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(packs, response.getBody().getData());

        verify(packService, times(1)).getPacksByCategorie(codCatpPfl);
    }

    @Test
    void getManagerPacks_shouldReturnManagerPacks() throws Exception {
        // Arrange
        List<PackDto> managerPacks = Arrays.asList(
                createTestPackDto("PACK001", "Manager Pack 1", true),
                createTestPackDto("PACK002", "Manager Pack 2", true)
        );
        when(packService.getManagerPacks()).thenReturn(managerPacks);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getManagerPacks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(managerPacks, response.getBody().getData());

        verify(packService, times(1)).getManagerPacks();
    }

    @Test
    void getManagerPacks_serviceThrowsException_shouldPropagateException() throws Exception {
        // Arrange
        when(packService.getManagerPacks())
                .thenThrow(new RuntimeException("Authorization error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> packController.getManagerPacks());

        verify(packService, times(1)).getManagerPacks();
    }

    @Test
    void getAvailablePacksForUser_validUser_shouldReturnPacks() throws Exception {
        // Arrange
        String targetUserMat = "USER001";
        List<PackDto> availablePacks = Arrays.asList(
                createTestPackDto("PACK001", "Available Pack 1", true),
                createTestPackDto("PACK002", "Available Pack 2", true)
        );
        when(packService.getAvailablePacksForUser(targetUserMat)).thenReturn(availablePacks);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getAvailablePacksForUser(targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(availablePacks, response.getBody().getData());

        verify(packService, times(1)).getAvailablePacksForUser(targetUserMat);
    }

    @Test
    void getAvailablePacksForUser_userNotFound_shouldThrowException() throws Exception {
        // Arrange
        String targetUserMat = "NONEXISTENT";
        when(packService.getAvailablePacksForUser(targetUserMat))
                .thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> packController.getAvailablePacksForUser(targetUserMat));

        verify(packService, times(1)).getAvailablePacksForUser(targetUserMat);
    }

    @Test
    void getNotAssignedPacksForUser_validUser_shouldReturnPacks() throws Exception {
        // Arrange
        String targetUserMat = "USER001";
        List<PackDto> notAssignedPacks = Arrays.asList(
                createTestPackDto("PACK003", "Not Assigned Pack 1", true),
                createTestPackDto("PACK004", "Not Assigned Pack 2", true)
        );
        when(packService.getAvailablePacksForUserNotAssigned(targetUserMat))
                .thenReturn(notAssignedPacks);

        // Act
        ResponseEntity<ResponseHabil> response = packController.getNotAssignedPacksForUser(targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(notAssignedPacks, response.getBody().getData());

        verify(packService, times(1)).getAvailablePacksForUserNotAssigned(targetUserMat);
    }

    @Test
    void getNotAssignedPacksForUser_emptyResult_shouldReturnEmptyList() throws Exception {
        // Arrange
        String targetUserMat = "USER001";
        when(packService.getAvailablePacksForUserNotAssigned(targetUserMat))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = packController.getNotAssignedPacksForUser(targetUserMat);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Collections.emptyList(), response.getBody().getData());

        verify(packService, times(1)).getAvailablePacksForUserNotAssigned(targetUserMat);
    }

    // ==================== HELPER METHODS ====================

    private PackDto createTestPackDto(String code, String libelle, boolean actif) {
        PackDto dto = new PackDto();
        dto.setCodPackPack(code);
        dto.setLibPackPack(libelle);
        dto.setBoolActifPack(actif ? 1 : 0);
        return dto;
    }
}