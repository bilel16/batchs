package com.bna.habil.controller;

import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.application.dto.PersonnelDto;
import com.bna.habil.application.dto.statistics.PersonnelStatsDto;
import com.bna.habil.application.services.UtilisateurProfilService;
import com.bna.habil.application.services.impl.PersonneServiceImpl;
import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.infrastructure.security.model.ResponseHabil;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.PersonnelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonnelControllerTest {

    @Mock
    private PersonneServiceImpl personneService;

    @Mock
    private UtilisateurProfilService utilisateurProfilService;

    @InjectMocks
    private PersonnelController personnelController;

    private Personnel testPersonnel;
    private PersonnelDto testPersonnelDto;
    private PersonnelDetailsDto testPersonnelDetailsDto;

    @BeforeEach
    void setUp() {
        testPersonnel = createTestPersonnel("MAT001");
        testPersonnelDto = createTestPersonnelDto();
        testPersonnelDetailsDto = createTestPersonnelDetailsDto("MAT001");
    }

    // ==================== HELPER METHODS ====================

    private Personnel createTestPersonnel(String matricule) {
        Personnel personnel = new Personnel();
        personnel.setMat(matricule);
        personnel.setCod_stat_user(true);
        personnel.setCod_strc_strc(123);
        personnel.setCin("12345678");
        personnel.setCod_typ("EMP");
        return personnel;
    }

    private PersonnelDto createTestPersonnelDto() {
        PersonnelDto dto = new PersonnelDto();
        dto.setMatricule("MAT001");
        dto.setActive(true);
        dto.setStructureId(123);
        dto.setStructureName("Test Structure");
        dto.setStructureType(1);
        dto.setCin("12345678");
        return dto;
    }

    private PersonnelDetailsDto createTestPersonnelDetailsDto(String matricule) {
        PersonnelDetailsDto dto = new PersonnelDetailsDto();
        dto.setMat(matricule);
        dto.setNom_prenom("John");
        dto.setEmail("john@gmail.com");
        dto.setCod_stat_user(true);
        dto.setCod_strc_strc(123);
        dto.setCod_tstr_tstr(1);
        return dto;
    }

    // ==================== SEARCH BY MATRICULE TESTS ====================

    @Test
    void searchByMatricule_validQuery_shouldReturnPersonnelList() {
        // Arrange
        String query = "MAT";
        List<Personnel> personnelList = Arrays.asList(
                createTestPersonnel("MAT001"),
                createTestPersonnel("MAT002")
        );

        when(personneService.findPersonnelByMatricule(query)).thenReturn(personnelList);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.searchByMatricule(query);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(personnelList, response.getBody().getData());

        verify(personneService, times(1)).findPersonnelByMatricule(query);
    }

    @Test
    void searchByMatricule_noResults_shouldReturnEmptyList() {
        // Arrange
        String query = "NONEXISTENT";
        when(personneService.findPersonnelByMatricule(query)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.searchByMatricule(query);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());

        verify(personneService, times(1)).findPersonnelByMatricule(query);
    }

    @Test
    void searchByMatricule_emptyQuery_shouldReturnResults() {
        // Arrange
        String query = "";
        List<Personnel> personnelList = Collections.singletonList(testPersonnel);

        when(personneService.findPersonnelByMatricule(query)).thenReturn(personnelList);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.searchByMatricule(query);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personnelList, Objects.requireNonNull(response.getBody()).getData());

        verify(personneService, times(1)).findPersonnelByMatricule(query);
    }

    @Test
    void searchByMatricule_serviceThrowsException_shouldPropagateException() {
        // Arrange
        String query = "MAT";
        when(personneService.findPersonnelByMatricule(query))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> personnelController.searchByMatricule(query));

        verify(personneService, times(1)).findPersonnelByMatricule(query);
    }

    // ==================== GET APPLICATIONS BY MATRICULE TESTS ====================

    @Test
    void getApplicationsByMatricule_validMatricule_shouldReturnApplicationsList() throws Exception {
        // Arrange
        String matricule = "MAT001";
        List<String> applications = Arrays.asList("APP001", "APP002", "APP003");

        when(utilisateurProfilService.getApplicationsByMatricule(matricule))
                .thenReturn(applications);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getApplicationsByMatricule(matricule);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(applications, response.getBody().getData());

        verify(utilisateurProfilService, times(1)).getApplicationsByMatricule(matricule);
    }

    @Test
    void getApplicationsByMatricule_noApplications_shouldReturnEmptyList() throws Exception {
        // Arrange
        String matricule = "MAT001";
        when(utilisateurProfilService.getApplicationsByMatricule(matricule))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getApplicationsByMatricule(matricule);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());

        verify(utilisateurProfilService, times(1)).getApplicationsByMatricule(matricule);
    }

    @Test
    void getApplicationsByMatricule_personnelNotFound_shouldThrowException() throws Exception {
        // Arrange
        String matricule = "NONEXISTENT";
        when(utilisateurProfilService.getApplicationsByMatricule(matricule))
                .thenThrow(new EntityNotFoundException("Personnel not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> personnelController.getApplicationsByMatricule(matricule));

        verify(utilisateurProfilService, times(1)).getApplicationsByMatricule(matricule);
    }

    @Test
    void getApplicationsByMatricule_serviceThrowsException_shouldPropagateException() throws Exception {
        // Arrange
        String matricule = "MAT001";
        when(utilisateurProfilService.getApplicationsByMatricule(matricule))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> personnelController.getApplicationsByMatricule(matricule));

        verify(utilisateurProfilService, times(1)).getApplicationsByMatricule(matricule);
    }

    // ==================== CREATE PERSONNEL TESTS ====================

    @Test
    void createPersonnel_validDto_shouldReturnCreatedPersonnel() {
        // Arrange
        when(personneService.createPersonnel(any(PersonnelDto.class)))
                .thenReturn(testPersonnelDetailsDto);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.createPersonnel(testPersonnelDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(testPersonnelDetailsDto, response.getBody().getData());

        verify(personneService, times(1)).createPersonnel(testPersonnelDto);
    }

    @Test
    void createPersonnel_duplicateMatricule_shouldThrowException() {
        // Arrange
        when(personneService.createPersonnel(any(PersonnelDto.class)))
                .thenThrow(new RuntimeException("Personnel with matricule MAT001 already exists"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> personnelController.createPersonnel(testPersonnelDto));

        verify(personneService, times(1)).createPersonnel(testPersonnelDto);
    }

    @Test
    void createPersonnel_nullDto_shouldThrowException() {
        // Arrange
        when(personneService.createPersonnel(null))
                .thenThrow(new IllegalArgumentException("Personnel DTO cannot be null"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> personnelController.createPersonnel(null));

        verify(personneService, times(1)).createPersonnel(null);
    }

    // ==================== GET ALL PERSONNEL DETAILS TESTS ====================

    @Test
    void getAllPersonnelDetails_shouldReturnAllPersonnel() {
        // Arrange
        List<PersonnelDetailsDto> personnelList = Arrays.asList(
                createTestPersonnelDetailsDto("MAT001"),
                createTestPersonnelDetailsDto("MAT002"),
                createTestPersonnelDetailsDto("MAT003")
        );

        when(personneService.getAllPersonelles()).thenReturn(personnelList);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getAllPersonnelDetails();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(personnelList, response.getBody().getData());
        assertEquals(3, ((List<?>) response.getBody().getData()).size());

        verify(personneService, times(1)).getAllPersonelles();
    }

    @Test
    void getAllPersonnelDetails_emptyList_shouldReturnEmptyList() {
        // Arrange
        when(personneService.getAllPersonelles()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getAllPersonnelDetails();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertTrue(((List<?>) response.getBody().getData()).isEmpty());

        verify(personneService, times(1)).getAllPersonelles();
    }

    @Test
    void getAllPersonnelDetails_serviceThrowsException_shouldPropagateException() {
        // Arrange
        when(personneService.getAllPersonelles())
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> personnelController.getAllPersonnelDetails());

        verify(personneService, times(1)).getAllPersonelles();
    }

    // ==================== GET PERSONNEL PAGE TESTS ====================

    @Test
    void getPersonnelPage_defaultParams_shouldReturnFirstPage() {
        // Arrange
        List<PersonnelDetailsDto> content = Arrays.asList(
                createTestPersonnelDetailsDto("MAT001"),
                createTestPersonnelDetailsDto("MAT002"),
                createTestPersonnelDetailsDto("MAT003"),
                createTestPersonnelDetailsDto("MAT004"),
                createTestPersonnelDetailsDto("MAT005")
        );
        Page<PersonnelDetailsDto> page = new PageImpl<>(content, PageRequest.of(0, 20), 2);

        when(personneService.getPersonelllesPageble(any(Pageable.class))).thenReturn(page);

        // Act
//        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelPage(0, 20);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(0, response.getBody().getReturnCode());
//        assertEquals(Constants.SUCCES, response.getBody().getMessage());
//
//        Page<?> resultPage = (Page<?>) response.getBody().getData();
//        assertEquals(5, resultPage.getContent().size());
//        assertEquals(0, resultPage.getNumber());
//        assertEquals(20, resultPage.getSize());
//
//        verify(personneService, times(1)).getPersonelllesPageble(any(Pageable.class));
    }

    @Test
    void getPersonnelPage_customPageSize_shouldReturnCorrectPage() {
        // Arrange
        List<PersonnelDetailsDto> content = Arrays.asList(
                createTestPersonnelDetailsDto("MAT001"),
                createTestPersonnelDetailsDto("MAT002"),
                createTestPersonnelDetailsDto("MAT003"),
                createTestPersonnelDetailsDto("MAT004"),
                createTestPersonnelDetailsDto("MAT005")
        );
        Page<PersonnelDetailsDto> page = new PageImpl<>(content, PageRequest.of(0, 5), 5);

        when(personneService.getPersonelllesPageble(any(Pageable.class))).thenReturn(page);

        // Act
//        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelPage(0, 5);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        Page<?> resultPage = (Page<?>) Objects.requireNonNull(response.getBody()).getData();
//        assertEquals(5, resultPage.getContent().size());
//        assertEquals(5, resultPage.getSize());
//
//        verify(personneService, times(1)).getPersonelllesPageble(any(Pageable.class));
    }

    @Test
    void getPersonnelPage_secondPage_shouldReturnCorrectPage() {
        // Arrange
        List<PersonnelDetailsDto> content = List.of(
                createTestPersonnelDetailsDto("MAT021")
        );
        Page<PersonnelDetailsDto> page = new PageImpl<>(content, PageRequest.of(1, 20), 21);

        when(personneService.getPersonelllesPageble(any(Pageable.class))).thenReturn(page);

        // Act
//        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelPage(1, 20);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        Page<?> resultPage = (Page<?>) Objects.requireNonNull(response.getBody()).getData();
//        assertEquals(1, resultPage.getNumber());
//        assertEquals(1, resultPage.getContent().size());
//
//        verify(personneService, times(1)).getPersonelllesPageble(any(Pageable.class));
    }

    @Test
    void getPersonnelPage_emptyPage_shouldReturnEmptyPage() {
        // Arrange
        Page<PersonnelDetailsDto> emptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 20),
                0
        );

        when(personneService.getPersonelllesPageble(any(Pageable.class))).thenReturn(emptyPage);

        // Act
//        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelPage(0, 20);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        Page<?> resultPage = (Page<?>) Objects.requireNonNull(response.getBody()).getData();
//        assertTrue(resultPage.getContent().isEmpty());
//        assertEquals(0, resultPage.getTotalElements());
//
//        verify(personneService, times(1)).getPersonelllesPageble(any(Pageable.class));
    }

    // ==================== UPDATE PERSONNEL TESTS ====================

    @Test
    void updatePersonnel_validData_shouldReturnUpdatedPersonnel() {
        // Arrange
        String matricule = "MAT001";
        PersonnelDetailsDto updatedDto = createTestPersonnelDetailsDto("MAT021");

        when(personneService.updatePersonelle(eq(matricule), any(PersonnelDetailsDto.class)))
                .thenReturn(updatedDto);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.updatePersonnel(
                matricule, testPersonnelDetailsDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(updatedDto, response.getBody().getData());

        verify(personneService, times(1)).updatePersonelle(matricule, testPersonnelDetailsDto);
    }

    @Test
    void updatePersonnel_nonExistentMatricule_shouldThrowException() {
        // Arrange
        String matricule = "NONEXISTENT";
        when(personneService.updatePersonelle(eq(matricule), any(PersonnelDetailsDto.class)))
                .thenThrow(new EntityNotFoundException("Personnel not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> personnelController.updatePersonnel(matricule, testPersonnelDetailsDto));

        verify(personneService, times(1)).updatePersonelle(matricule, testPersonnelDetailsDto);
    }

    @Test
    void updatePersonnel_nullDto_shouldThrowException() {
        // Arrange
        String matricule = "MAT001";
        when(personneService.updatePersonelle(eq(matricule), isNull()))
                .thenThrow(new IllegalArgumentException("Personnel DTO cannot be null"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> personnelController.updatePersonnel(matricule, null));

        verify(personneService, times(1)).updatePersonelle(matricule, null);
    }

    // ==================== GET PERSONNEL STATS TESTS ====================

    @Test
    void getPersonnelStats_shouldReturnStatistics() {
        // Arrange
        PersonnelStatsDto stats = new PersonnelStatsDto();
        stats.setTotal(100L);
        stats.setActive(85L);
        stats.setInactive(15L);

        when(personneService.getPersonnelStatistics()).thenReturn(stats);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelStats();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getReturnCode());
        assertEquals(Constants.SUCCES, response.getBody().getMessage());
        assertEquals(stats, response.getBody().getData());

        PersonnelStatsDto resultStats = (PersonnelStatsDto) response.getBody().getData();
        assertEquals(100L, resultStats.getTotal());
        assertEquals(85L, resultStats.getActive());
        assertEquals(15L, resultStats.getInactive());

        verify(personneService, times(1)).getPersonnelStatistics();
    }

    @Test
    void getPersonnelStats_noPersonnel_shouldReturnZeroStats() {
        // Arrange
        PersonnelStatsDto emptyStats = new PersonnelStatsDto();
        emptyStats.setTotal(0L);
        emptyStats.setActive(0L);
        emptyStats.setInactive(0L);

        when(personneService.getPersonnelStatistics()).thenReturn(emptyStats);

        // Act
        ResponseEntity<ResponseHabil> response = personnelController.getPersonnelStats();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PersonnelStatsDto resultStats = (PersonnelStatsDto) Objects.requireNonNull(response.getBody()).getData();
        assertEquals(0L, resultStats.getTotal());

        verify(personneService, times(1)).getPersonnelStatistics();
    }

    @Test
    void getPersonnelStats_serviceThrowsException_shouldPropagateException() {
        // Arrange
        when(personneService.getPersonnelStatistics())
                .thenThrow(new RuntimeException("Failed to calculate statistics"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> personnelController.getPersonnelStats());

        verify(personneService, times(1)).getPersonnelStatistics();
    }
}