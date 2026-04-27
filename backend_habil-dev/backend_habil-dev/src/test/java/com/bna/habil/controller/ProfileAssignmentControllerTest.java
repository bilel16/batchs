package com.bna.habil.controller;

import com.bna.habil.application.dto.PersonnelDetailsDto;
import com.bna.habil.application.dto.ProfilDto;


import com.bna.habil.application.services.impl.ProfileManagementService;
import com.bna.habil.application.services.impl.UtilisateurProfilServiceImpl;
import com.bna.habil.infrastructure.utils.AssignmentStatistics;
import com.bna.habil.interfaces.request.*;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import com.bna.habil.interfaces.response.ProfileUpdateResult;
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
class ProfileAssignmentControllerTest {

    @Mock
    private UtilisateurProfilServiceImpl profileService;

    @Mock
    private ProfileManagementService profileManagementService;

    @InjectMocks
    private com.bna.habil.interfaces.controllers.ProfileAssignmentController controller;

    private String managerMatricule;
    private String userMatricule;
    private String appCode;
    private String profileCode;

    @BeforeEach
    void setUp() {
        managerMatricule = "MGR001";
        userMatricule = "USER001";
        appCode = "APP001";
        profileCode = "PROF001";
    }

    @Test
    void getManagedUsers_shouldReturnSetOfUsers() {
        Set<String> managedUsers = new HashSet<>(Arrays.asList("USER001", "USER002"));
        when(profileService.getManagedUsers(managerMatricule)).thenReturn(managedUsers);

        ResponseEntity<Set<String>> response = controller.getManagedUsers(managerMatricule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(managedUsers, response.getBody());
        verify(profileService, times(1)).getManagedUsers(managerMatricule);
    }

    @Test
    void getManagedUsersWithDetails_shouldReturnListOfDetails() {
        List<PersonnelDetailsDto> details = Arrays.asList(new PersonnelDetailsDto(), new PersonnelDetailsDto());
        when(profileService.getManagedUsersWithDetails(managerMatricule)).thenReturn(details);

        ResponseEntity<List<PersonnelDetailsDto>> response = controller.getManagedUsersWithDetails(managerMatricule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(details, response.getBody());
        verify(profileService, times(1)).getManagedUsersWithDetails(managerMatricule);
    }

    @Test
    void getAssignableProfiles_shouldReturnListOfProfiles() {
        List<ProfilDto> profiles = Arrays.asList(new ProfilDto(), new ProfilDto());
        when(profileService.getAssignableProfiles(managerMatricule, userMatricule, appCode)).thenReturn(profiles);

        ResponseEntity<List<ProfilDto>> response = controller.getAssignableProfiles(managerMatricule, userMatricule, appCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profiles, response.getBody());
        verify(profileService, times(1)).getAssignableProfiles(managerMatricule, userMatricule, appCode);
    }

    @Test
    void canAssign_shouldReturnBoolean() {
        when(profileService.canManagerAssignProfile(managerMatricule, profileCode, appCode)).thenReturn(true);

        ResponseEntity<Boolean> response = controller.canAssign(managerMatricule, profileCode, appCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
        verify(profileService, times(1)).canManagerAssignProfile(managerMatricule, profileCode, appCode);
    }

    @Test
    void getUserProfiles_shouldReturnListOfProfiles() {
        List<ProfilDto> profiles = Arrays.asList(new ProfilDto(), new ProfilDto());
        when(profileService.getUserProfiles(userMatricule)).thenReturn(profiles);

        ResponseEntity<List<ProfilDto>> response = controller.getUserProfiles(userMatricule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profiles, response.getBody());
        verify(profileService, times(1)).getUserProfiles(userMatricule);
    }

    @Test
    void assignProfile_success_shouldReturnSuccessMessage() throws Exception {
        ProfileAssignmentRequest request = new ProfileAssignmentRequest();
        request.setManagerMatricule(managerMatricule);
        request.setUserMatricule(userMatricule);
        request.setProfileCode(profileCode);
        request.setAppCode(appCode);

        doNothing().when(profileService).assignProfile(managerMatricule, userMatricule, profileCode, appCode);

        ResponseEntity<Map<String, String>> response = controller.assignProfile(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Profile assigned successfully", response.getBody().get("message"));
        verify(profileService, times(1)).assignProfile(managerMatricule, userMatricule, profileCode, appCode);
    }

    @Test
    void assignProfile_failure_shouldReturnErrorMessage() throws Exception {
        ProfileAssignmentRequest request = new ProfileAssignmentRequest();
        request.setManagerMatricule(managerMatricule);
        request.setUserMatricule(userMatricule);
        request.setProfileCode(profileCode);
        request.setAppCode(appCode);

        doThrow(new RuntimeException("Assign error")).when(profileService).assignProfile(managerMatricule, userMatricule, profileCode, appCode);

        ResponseEntity<Map<String, String>> response = controller.assignProfile(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Assign error", response.getBody().get("error"));
        verify(profileService, times(1)).assignProfile(managerMatricule, userMatricule, profileCode, appCode);
    }

    @Test
    void bulkAssign_shouldReturnBatchAssignmentResult() {
        BulkAssignmentRequest request = new BulkAssignmentRequest();
        request.setManagerMatricule(managerMatricule);
        request.setUserMatricules(Arrays.asList(userMatricule, "USER002"));
        request.setProfileCode(profileCode);
        request.setAppCode(appCode);

        BatchAssignmentResult result = new BatchAssignmentResult();
        when(profileService.bulkAssignProfile(managerMatricule, request.getUserMatricules(), profileCode, appCode)).thenReturn(result);

        ResponseEntity<BatchAssignmentResult> response = controller.bulkAssign(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
        verify(profileService, times(1)).bulkAssignProfile(managerMatricule, request.getUserMatricules(), profileCode, appCode);
    }

    @Test
    void removeProfile_success_shouldReturnSuccessMessage() throws Exception {
        doNothing().when(profileService).removeProfile(userMatricule, profileCode);

        ResponseEntity<Map<String, String>> response = controller.removeProfile(userMatricule, profileCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Profile removed successfully", response.getBody().get("message"));
        verify(profileService, times(1)).removeProfile(userMatricule, profileCode);
    }

    @Test
    void removeProfile_failure_shouldReturnErrorMessage() throws Exception {
        doThrow(new RuntimeException("Remove error")).when(profileService).removeProfile(userMatricule, profileCode);

        ResponseEntity<Map<String, String>> response = controller.removeProfile(userMatricule, profileCode);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Remove error", response.getBody().get("error"));
        verify(profileService, times(1)).removeProfile(userMatricule, profileCode);
    }

    @Test
    void getStatistics_shouldReturnAssignmentStatistics() {
        AssignmentStatistics stats = new AssignmentStatistics();
        when(profileService.getStatistics(managerMatricule)).thenReturn(stats);

        ResponseEntity<AssignmentStatistics> response = controller.getStatistics(managerMatricule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stats, response.getBody());
        verify(profileService, times(1)).getStatistics(managerMatricule);
    }

    @Test
    void assignMultipleProfiles_validRequest_shouldReturnOk() {
        UserProfilesAssignmentRequest request = new UserProfilesAssignmentRequest();
        request.setUserMatricule(userMatricule);
        request.setAppCode(appCode);

        AssignedProfile p1 = new AssignedProfile("P1", new Date(), null, 1);
        AssignedProfile p2 = new AssignedProfile("P2", new Date(), null, 1);

        request.setAssignedProfiles(Arrays.asList(p1, p2));
        request.setRevokedProfiles(List.of("P3"));

        BatchAssignmentResult result = new BatchAssignmentResult();
        result.addSuccess("P1");
        result.addSuccess("P2");
        result.addSuccess("P3");

        when(profileService.assignMultipleProfilesToUser(
                eq(userMatricule),
                anyList(),
                anyList(),
                eq(appCode)))
                .thenReturn(result);

        ResponseEntity<BatchAssignmentResult> response = controller.assignMultipleProfiles(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
    }

    @Test
    void assignMultipleProfiles_partialSuccess_shouldReturnMultiStatus() {
        UserProfilesAssignmentRequest request = new UserProfilesAssignmentRequest();
        request.setUserMatricule(userMatricule);
        request.setAppCode(appCode);

        AssignedProfile p1 = new AssignedProfile("P1", new Date(), null, 1);
        request.setAssignedProfiles(List.of(p1));
        request.setRevokedProfiles(List.of("P2"));

        BatchAssignmentResult result = new BatchAssignmentResult();
        result.addSuccess("P1");
        result.addFailure("P2", "Some error");

        when(profileService.assignMultipleProfilesToUser(
                eq(userMatricule),
                anyList(),
                anyList(),
                eq(appCode)))
                .thenReturn(result);

        ResponseEntity<BatchAssignmentResult> response = controller.assignMultipleProfiles(request);

        assertEquals(HttpStatus.MULTI_STATUS, response.getStatusCode());
        assertEquals(result, response.getBody());
    }

    @Test
    void assignMultipleProfiles_allFailed_shouldReturnBadRequest() {
        UserProfilesAssignmentRequest request = new UserProfilesAssignmentRequest();
        request.setUserMatricule(userMatricule);
        request.setAppCode(appCode);

        AssignedProfile p1 = new AssignedProfile("P1", new Date(), null, 1);
        request.setAssignedProfiles(Arrays.asList(p1));
        request.setRevokedProfiles(Arrays.asList("P2"));

        BatchAssignmentResult result = new BatchAssignmentResult();
        result.addFailure("P1", "Some error");
        result.addFailure("P2", "Another error");

        when(profileService.assignMultipleProfilesToUser(
                eq(userMatricule),
                anyList(),
                anyList(),
                eq(appCode)))
                .thenReturn(result);

        ResponseEntity<BatchAssignmentResult> response = controller.assignMultipleProfiles(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(result, response.getBody());
    }

    @Test
    void assignMultipleProfiles_invalidRequest_shouldReturnBadRequest() {
        UserProfilesAssignmentRequest request = new UserProfilesAssignmentRequest();
        request.setUserMatricule("");
        request.setAppCode("");

        ResponseEntity<BatchAssignmentResult> response = controller.assignMultipleProfiles(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).getFailed().containsKey("REQUEST"));
    }

    @Test
    void assignMultipleProfiles_noAssignmentsOrRevocations_shouldReturnEmptyResult() {
        UserProfilesAssignmentRequest request = new UserProfilesAssignmentRequest();
        request.setUserMatricule(userMatricule);
        request.setAppCode(appCode);
        request.setAssignedProfiles(Collections.emptyList());
        request.setRevokedProfiles(Collections.emptyList());

        ResponseEntity<BatchAssignmentResult> response = controller.assignMultipleProfiles(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).getSuccessCount());
        assertEquals(0, response.getBody().getFailureCount());
    }

    @Test
    void batchUpdateProfiles_success_shouldReturnOk() {
        UserProfileUpdateRequest request = new UserProfileUpdateRequest();
        ProfileUpdateResult result = new ProfileUpdateResult();
        result.setSuccess(true);

        when(profileManagementService.updateUserProfiles(request)).thenReturn(result);

        ResponseEntity<ProfileUpdateResult> response = controller.batchUpdateProfiles(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
    }

    @Test
    void batchUpdateProfiles_partialSuccess_shouldReturnPartialContent() {
        UserProfileUpdateRequest request = new UserProfileUpdateRequest();
        ProfileUpdateResult result = new ProfileUpdateResult();
        result.setSuccess(false);

        when(profileManagementService.updateUserProfiles(request)).thenReturn(result);

        ResponseEntity<ProfileUpdateResult> response = controller.batchUpdateProfiles(request);

        assertEquals(HttpStatus.PARTIAL_CONTENT, response.getStatusCode());
        assertEquals(result, response.getBody());
    }

}