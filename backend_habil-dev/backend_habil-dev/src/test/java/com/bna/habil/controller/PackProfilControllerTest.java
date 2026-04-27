package com.bna.habil.controller;

import com.bna.habil.application.dto.PackProfilDto;
import com.bna.habil.application.services.PackProfilService;
import com.bna.habil.application.services.crud.model.BatchOperationResult;
import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import com.bna.habil.domain.exceptions.BatchOperationException;
import com.bna.habil.domain.exceptions.DuplicateResourceException;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.PackProfilController;
import com.bna.habil.interfaces.response.SyncResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackProfilControllerTest {

    @Mock
    private PackProfilService packProfilService;

    @InjectMocks
    private PackProfilController packProfilController;

    private PackProfilDto testPackProfilDto;
    private PackProfilId testPackProfilId;

    @BeforeEach
    void setUp() {
        testPackProfilDto = createTestPackProfilDto("PACK001", "PROF001");
        testPackProfilId = new PackProfilId("PACK001", "PROF001");
    }

    // ==================== HELPER METHODS ====================

    private PackProfilDto createTestPackProfilDto(String packCode, String profilCode) {
        PackProfilDto dto = new PackProfilDto();
        dto.setCodPackPack(packCode);
        dto.setCodPflPfl(profilCode);
        dto.setBoolEtat(1);
        return dto;
    }

    // ==================== CREATE TESTS ====================

    @Test
    void create_validDto_shouldReturnCreated() {
        // Arrange
        when(packProfilService.create(any(PackProfilDto.class)))
                .thenReturn(testPackProfilDto);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.create(testPackProfilDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.CREATED, response.getBody().getMessage());
        assertEquals(testPackProfilDto, response.getBody().getData());

        verify(packProfilService, times(1)).create(testPackProfilDto);
    }

    @Test
    void create_serviceThrowsDuplicateException_shouldPropagateException() {
        // Arrange
        when(packProfilService.create(any(PackProfilDto.class)))
                .thenThrow(new DuplicateResourceException("Pack-Profile already exists"));

        // Act & Assert
        assertThrows(DuplicateResourceException.class,
                () -> packProfilController.create(testPackProfilDto));

        verify(packProfilService, times(1)).create(testPackProfilDto);
    }

    // ==================== BATCH CREATE TESTS ====================

    @Test
    void createBatch_allSuccessful_shouldReturnCreated() {
        // Arrange
        List<PackProfilDto> batch = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK001", "PROF002")
        );

        when(packProfilService.createBatch(anyList())).thenReturn(batch);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.createBatch(
                batch, BatchOperationResult.BatchMode.ALL_OR_NOTHING);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(response.getBody().getMessage().contains("2 items"));

        verify(packProfilService, times(1)).createBatch(batch);
    }

    @Test
    void createBatch_withDuplicatesInBatch_allOrNothing_shouldReturnConflict() {
        // Arrange - Same pack-profile combination twice
        List<PackProfilDto> batch = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK001", "PROF001")  // Duplicate
        );

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.createBatch(
                batch, BatchOperationResult.BatchMode.ALL_OR_NOTHING);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReturnCode());
        assertEquals("DUPLICATE_ENTRIES_IN_BATCH", response.getBody().getMessage());

        // Service should NOT be called when duplicates are detected
        verify(packProfilService, never()).createBatch(anyList());
    }

    @Test
    void createBatch_withDuplicatesInBatch_bestEffort_shouldProcessNonDuplicates() {
        // Arrange
        List<PackProfilDto> batch = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK001", "PROF001"),  // Duplicate
                createTestPackProfilDto("PACK001", "PROF002")
        );

        List<PackProfilDto> successful = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK001", "PROF002")
        );

        when(packProfilService.createBatch(anyList())).thenReturn(successful);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.createBatch(
                batch, BatchOperationResult.BatchMode.BEST_EFFORT);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(207, response.getBody().getReturnCode());
        assertTrue(response.getBody().getMessage().contains("succeeded"));

        verify(packProfilService, times(1)).createBatch(anyList());
    }

    @Test
    void createBatch_serviceThrowsBatchOperationException_shouldReturnPartialSuccess() {
        // Arrange
        List<PackProfilDto> batch = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK001", "PROF002")
        );

        List<BatchOperationException.BatchError> errors = Arrays.asList(
                new BatchOperationException.BatchError(1, "Pack=PACK001,Profile=PROF002", "Validation failed")
        );

        when(packProfilService.createBatch(anyList()))
                .thenThrow(new BatchOperationException("Batch failed", errors));

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.createBatch(
                batch, BatchOperationResult.BatchMode.BEST_EFFORT);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReturnCode());
        assertTrue(response.getBody().getMessage().contains("failed"));

        verify(packProfilService, times(1)).createBatch(batch);
    }

    @Test
    void createBatch_serviceThrowsDuplicateResourceException_shouldReturnConflict() {
        // Arrange
        List<PackProfilDto> batch = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001")
        );

        when(packProfilService.createBatch(anyList()))
                .thenThrow(new DuplicateResourceException("Already exists"));

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.createBatch(
                batch, BatchOperationResult.BatchMode.ALL_OR_NOTHING);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReturnCode());

        verify(packProfilService, times(1)).createBatch(batch);
    }

    // ==================== GET ALL TESTS ====================

    @Test
    void getAll_shouldReturnAllPackProfils() {
        // Arrange
        List<PackProfilDto> packProfils = Arrays.asList(
                createTestPackProfilDto("PACK001", "PROF001"),
                createTestPackProfilDto("PACK002", "PROF002")
        );

        when(packProfilService.findAll()).thenReturn(packProfils);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(packProfils, response.getBody().getData());

        verify(packProfilService, times(1)).findAll();
    }

    @Test
    void getAll_emptyList_shouldReturnEmptyList() {
        // Arrange
        when(packProfilService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());

        verify(packProfilService, times(1)).findAll();
    }

    // ==================== GET BY PACK TESTS ====================

    @Test
    void getByPack_validPackCode_shouldReturnPackProfils() throws Exception {
        // Arrange
        String packCode = "PACK001";
        List<PackProfilDto> packProfils = Arrays.asList(
                createTestPackProfilDto(packCode, "PROF001"),
                createTestPackProfilDto(packCode, "PROF002")
        );

        when(packProfilService.getPackProfilListByPack(packCode)).thenReturn(packProfils);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getByPack(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(packProfils, response.getBody().getData());

        verify(packProfilService, times(1)).getPackProfilListByPack(packCode);
    }

    @Test
    void getByPack_nonExistentPack_shouldThrowException() throws Exception {
        // Arrange
        String packCode = "NONEXISTENT";
        when(packProfilService.getPackProfilListByPack(packCode))
                .thenThrow(new EntityNotFoundException("Pack not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> packProfilController.getByPack(packCode));

        verify(packProfilService, times(1)).getPackProfilListByPack(packCode);
    }

    // ==================== GET BY PROFILE TESTS ====================

    @Test
    void getByProfile_validProfileCode_shouldReturnPackProfils() throws Exception {
        // Arrange
        String profileCode = "PROF001";
        List<PackProfilDto> packProfils = Arrays.asList(
                createTestPackProfilDto("PACK001", profileCode),
                createTestPackProfilDto("PACK002", profileCode)
        );

        when(packProfilService.getPackProfilListByProfile(profileCode)).thenReturn(packProfils);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getByProfile(profileCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(packProfils, response.getBody().getData());

        verify(packProfilService, times(1)).getPackProfilListByProfile(profileCode);
    }

    // ==================== GET ACTIVE BY PACK TESTS ====================

    @Test
    void getActiveByPack_validPackCode_shouldReturnActivePackProfils() throws Exception {
        // Arrange
        String packCode = "PACK001";
        List<PackProfilDto> activeProfils = Arrays.asList(
                createTestPackProfilDto(packCode, "PROF001")
        );

        when(packProfilService.getActivePackProfilsByPack(packCode)).thenReturn(activeProfils);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getActiveByPack(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(activeProfils, response.getBody().getData());

        verify(packProfilService, times(1)).getActivePackProfilsByPack(packCode);
    }

    // ==================== GET BY STRUCTURE TYPE TESTS ====================

    @Test
    void getByStructureType_validParams_shouldReturnPackProfils() throws Exception {
        // Arrange
        String packCode = "PACK001";
        String structureType = "STRUCT001";
        List<PackProfilDto> packProfils = Arrays.asList(
                createTestPackProfilDto(packCode, "PROF001")
        );

        when(packProfilService.getPackProfilsByStructureType(packCode, structureType))
                .thenReturn(packProfils);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.getByStructureType(
                packCode, structureType);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(packProfils, response.getBody().getData());

        verify(packProfilService, times(1))
                .getPackProfilsByStructureType(packCode, structureType);
    }

    // ==================== UPDATE TESTS ====================

    @Test
    void update_validDto_shouldReturnUpdated() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";
        PackProfilDto updatedDto = createTestPackProfilDto(packCode, profilCode);

        when(packProfilService.update(any(PackProfilId.class), any(PackProfilDto.class)))
                .thenReturn(updatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.update(
                packCode, profilCode, updatedDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.UPDATED, response.getBody().getMessage());
        assertEquals(updatedDto, response.getBody().getData());

        verify(packProfilService, times(1)).update(any(PackProfilId.class), eq(updatedDto));
    }

    @Test
    void update_nonExistent_shouldThrowException() {
        // Arrange
        String packCode = "NONEXISTENT";
        String profilCode = "PROF001";

        when(packProfilService.update(any(PackProfilId.class), any(PackProfilDto.class)))
                .thenThrow(new EntityNotFoundException("PackProfil not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> packProfilController.update(packCode, profilCode, testPackProfilDto));

        verify(packProfilService, times(1)).update(any(PackProfilId.class), eq(testPackProfilDto));
    }

    // ==================== UPDATE STATUS TESTS ====================

    @Test
    void updateStatus_validParams_shouldReturnSuccess() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";
        Integer status = 1;

        doNothing().when(packProfilService)
                .updatePackProfilStatus(packCode, profilCode, status);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.updateStatus(
                packCode, profilCode, status);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.UPDATED, response.getBody().getMessage());

        verify(packProfilService, times(1))
                .updatePackProfilStatus(packCode, profilCode, status);
    }

    // ==================== DEACTIVATE TESTS ====================

    @Test
    void deactivate_validParams_shouldReturnDeactivated() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";
        PackProfilDto deactivatedDto = createTestPackProfilDto(packCode, profilCode);
        deactivatedDto.setBoolEtat(0);

        when(packProfilService.deactivatePackProfil(any(PackProfilId.class)))
                .thenReturn(deactivatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.deactivate(
                packCode, profilCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(deactivatedDto, response.getBody().getData());

        verify(packProfilService, times(1)).deactivatePackProfil(any(PackProfilId.class));
    }

    // ==================== ACTIVATE TESTS ====================

    @Test
    void activate_validParams_shouldReturnActivated() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";
        PackProfilDto activatedDto = createTestPackProfilDto(packCode, profilCode);
        activatedDto.setBoolEtat(1);

        when(packProfilService.activatePackProfil(any(PackProfilId.class)))
                .thenReturn(activatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.activate(
                packCode, profilCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(activatedDto, response.getBody().getData());

        verify(packProfilService, times(1)).activatePackProfil(any(PackProfilId.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    void delete_validParams_shouldReturnSuccess() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";

        doNothing().when(packProfilService).delete(any(PackProfilId.class));

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.delete(
                packCode, profilCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());

        verify(packProfilService, times(1)).delete(any(PackProfilId.class));
    }

    @Test
    void delete_nonExistent_shouldThrowException() {
        // Arrange
        String packCode = "NONEXISTENT";
        String profilCode = "PROF001";

        doThrow(new EntityNotFoundException("PackProfil not found"))
                .when(packProfilService).delete(any(PackProfilId.class));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> packProfilController.delete(packCode, profilCode));

        verify(packProfilService, times(1)).delete(any(PackProfilId.class));
    }

    // ==================== DELETE BATCH TESTS ====================

    @Test
    void deleteMultipleFromPack_validParams_shouldReturnSuccess() {
        // Arrange
        String packCode = "PACK001";
        List<String> profilCodes = Arrays.asList("PROF001", "PROF002", "PROF003");

        doNothing().when(packProfilService).deleteProfilsFromPack(packCode, profilCodes);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.deleteMultipleFromPack(
                packCode, profilCodes);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(response.getBody().getData().toString().contains("3 profiles deleted"));

        verify(packProfilService, times(1)).deleteProfilsFromPack(packCode, profilCodes);
    }

    // ==================== SYNC TESTS ====================

    @Test
    void syncPackProfiles_validPack_shouldReturnSyncResult() {
        // Arrange
        String packCode = "PACK001";
        SyncResult syncResult = new SyncResult(5, 2, 1);

        when(packProfilService.autoSyncPackProfiles(packCode)).thenReturn(syncResult);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.syncPackProfiles(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(response.getBody().getMessage().contains("5 users updated"));
        assertTrue(response.getBody().getMessage().contains("2 profiles added"));
        assertTrue(response.getBody().getMessage().contains("1 profiles removed"));
        assertEquals(syncResult, response.getBody().getData());

        verify(packProfilService, times(1)).autoSyncPackProfiles(packCode);
    }

    @Test
    void syncPackProfiles_packNotFound_shouldReturnNotFound() {
        // Arrange
        String packCode = "NONEXISTENT";

        when(packProfilService.autoSyncPackProfiles(packCode))
                .thenThrow(new EntityNotFoundException("Pack not found"));

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.syncPackProfiles(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReturnCode());
        assertEquals("Pack not found", response.getBody().getMessage());

        verify(packProfilService, times(1)).autoSyncPackProfiles(packCode);
    }

    @Test
    void syncPackProfiles_unexpectedError_shouldReturnInternalServerError() {
        // Arrange
        String packCode = "PACK001";

        when(packProfilService.autoSyncPackProfiles(packCode))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.syncPackProfiles(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getReturnCode());
        assertTrue(response.getBody().getMessage().contains("Error syncing profiles"));

        verify(packProfilService, times(1)).autoSyncPackProfiles(packCode);
    }

    // ==================== COUNT TESTS ====================

    @Test
    void countProfilesInPack_validPack_shouldReturnCount() {
        // Arrange
        String packCode = "PACK001";
        int count = 5;

        when(packProfilService.countProfilesInPack(packCode)).thenReturn(count);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.countProfilesInPack(packCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().getData();
        assertEquals(packCode, data.get("codPackPack"));
        assertEquals(count, data.get("count"));

        verify(packProfilService, times(1)).countProfilesInPack(packCode);
    }

    // ==================== EXISTS TESTS ====================

    @Test
    void checkExists_profileExistsInPack_shouldReturnTrue() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "PROF001";

        when(packProfilService.isProfileInPack(packCode, profilCode)).thenReturn(true);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.checkExists(
                packCode, profilCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().getData();
        assertEquals(true, data.get("exists"));

        verify(packProfilService, times(1)).isProfileInPack(packCode, profilCode);
    }

    @Test
    void checkExists_profileNotInPack_shouldReturnFalse() {
        // Arrange
        String packCode = "PACK001";
        String profilCode = "NONEXISTENT";

        when(packProfilService.isProfileInPack(packCode, profilCode)).thenReturn(false);

        // Act
        ResponseEntity<ResponseHabil> response = packProfilController.checkExists(
                packCode, profilCode);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().getData();
        assertEquals(false, data.get("exists"));

        verify(packProfilService, times(1)).isProfileInPack(packCode, profilCode);
    }
}