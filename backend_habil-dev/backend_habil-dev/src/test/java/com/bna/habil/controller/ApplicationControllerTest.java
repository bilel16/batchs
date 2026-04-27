package com.bna.habil.controller;

import com.bna.habil.application.dto.AddApplicationDto;
import com.bna.habil.application.dto.ApplicationDto;
import com.bna.habil.application.services.ApplicationService;
import com.bna.habil.config.TestSecurityConfig;
import com.bna.habil.domain.exceptions.ResourceNotFoundException;
import com.bna.habil.infrastructure.config.GlobalExceptionHandler;
import com.bna.habil.infrastructure.security.filter.JwtRequestFilter;
import com.bna.habil.infrastructure.utils.Constants;
import com.bna.habil.interfaces.controllers.ApplicationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ApplicationController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtRequestFilter.class
        ))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})  // ← Added GlobalExceptionHandler
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) //Even if DEV DB is configured → it will be IGNORED

class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ApplicationDto validApplicationDto;
    private AddApplicationDto validAddApplicationDto;

    @BeforeEach
    void setUp() {
        validApplicationDto = new ApplicationDto();
        validApplicationDto.setCodApp("APP001");
        validApplicationDto.setLibApp("Test Application");
        validApplicationDto.setLibLab("Test Label");

        validAddApplicationDto = new AddApplicationDto();
        validAddApplicationDto.setCodApp("APP002");
        validAddApplicationDto.setLibApp("Test App with Profiles");
        validAddApplicationDto.setLibLab("Test Label");
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        GET /api/applications                                 ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("GET /api/applications - Get All Applications")
    class GetAllApplicationsTests {

        @Test
        @DisplayName("✅ URL: Should map to GET /api/applications")
        @WithMockUser(roles = {"USER"})
        void shouldMapCorrectUrl() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("✅ Method: Should only accept GET requests")
        @WithMockUser(roles = {"USER"})
        void shouldOnlyAcceptGetMethod() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("🔒 Security: Should return 401 when not authenticated")
        void shouldReturn401WhenNotAuthenticated() throws Exception {
            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("🔒 Security: Should allow USER role to access")
        @WithMockUser(roles = {"USER"})
        void shouldAllowUserRole() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("🔒 Security: Should allow ADMIN role to access")
        @WithMockUser(roles = {"ADMIN"})
        void shouldAllowAdminRole() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("📦 Response: Should return 200 OK")
        @WithMockUser(roles = {"USER"})
        void shouldReturn200() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("📦 Response: Should return correct response structure")
        @WithMockUser(roles = {"USER"})
        void shouldReturnCorrectStructure() throws Exception {
            List<ApplicationDto> apps = List.of(validApplicationDto);
            when(applicationService.findAll()).thenReturn(apps);

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.message").value(Constants.SUCCES))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data", hasSize(1)))
                    .andExpect(jsonPath("$.data[0].codApp").value("APP001"))
                    .andExpect(jsonPath("$.data[0].libApp").value("Test Application"))
                    .andExpect(jsonPath("$.data[0].libLab").value("Test Label"));
        }

        @Test
        @DisplayName("📦 Response: Should return empty array when no applications")
        @WithMockUser(roles = {"USER"})
        void shouldReturnEmptyArray() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data", hasSize(0)));
        }

        @Test
        @DisplayName("📦 Response: Should return multiple applications")
        @WithMockUser(roles = {"USER"})
        void shouldReturnMultipleApplications() throws Exception {
            ApplicationDto app2 = new ApplicationDto();
            app2.setCodApp("APP002");
            app2.setLibApp("Second App");
            app2.setLibLab("Second Label");

            when(applicationService.findAll()).thenReturn(List.of(validApplicationDto, app2));

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.data", hasSize(2)))
                    .andExpect(jsonPath("$.data[0].codApp").value("APP001"))
                    .andExpect(jsonPath("$.data[1].codApp").value("APP002"));
        }

        @Test
        @DisplayName("🔗 Service: Should call findAll() exactly once")
        @WithMockUser(roles = {"USER"})
        void shouldCallFindAllOnce() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());

            verify(applicationService, times(1)).findAll();
            verifyNoMoreInteractions(applicationService);
        }

        @Test
        @DisplayName("🔗 Service: Should not call any other service methods")
        @WithMockUser(roles = {"USER"})
        void shouldNotCallOtherMethods() throws Exception {
            when(applicationService.findAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isOk());

            verify(applicationService, never()).findById(any());
            verify(applicationService, never()).create(any());
            verify(applicationService, never()).update(any(), any());
            verify(applicationService, never()).delete(any());
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        GET /api/applications/{id}                            ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("GET /api/applications/{id} - Get Application By ID")
    class GetApplicationByIdTests {

        @Test
        @DisplayName("✅ URL: Should map to GET /api/applications/{id}")
        @WithMockUser(roles = {"USER"})
        void shouldMapCorrectUrl() throws Exception {
            when(applicationService.findById("APP001")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("✅ URL: Should handle different path variable values")
        @WithMockUser(roles = {"USER"})
        void shouldHandleDifferentPathVariables() throws Exception {
            when(applicationService.findById("TEST-APP-123")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "TEST-APP-123"))
                    .andExpect(status().isOk());

            verify(applicationService).findById("TEST-APP-123");
        }

        @Test
        @DisplayName("✅ URL: Should handle special characters in path variable")
        @WithMockUser(roles = {"USER"})
        void shouldHandleSpecialCharacters() throws Exception {
            String specialId = "APP_001-test";
            when(applicationService.findById(specialId)).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", specialId))
                    .andExpect(status().isOk());

            verify(applicationService).findById(specialId);
        }

        @Test
        @DisplayName("🔒 Security: Should return 401 when not authenticated")
        void shouldReturn401WhenNotAuthenticated() throws Exception {
            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("🔒 Security: Should allow USER role")
        @WithMockUser(roles = {"USER"})
        void shouldAllowUserRole() throws Exception {
            when(applicationService.findById("APP001")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("🔒 Security: Should allow ADMIN role")
        @WithMockUser(roles = {"ADMIN"})
        void shouldAllowAdminRole() throws Exception {
            when(applicationService.findById("APP001")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("📦 Response: Should return 200 with application data")
        @WithMockUser(roles = {"USER"})
        void shouldReturn200WithData() throws Exception {
            when(applicationService.findById("APP001")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.message").value(Constants.SUCCES))
                    .andExpect(jsonPath("$.data.codApp").value("APP001"))
                    .andExpect(jsonPath("$.data.libApp").value("Test Application"))
                    .andExpect(jsonPath("$.data.libLab").value("Test Label"));
        }

        @Test
        @DisplayName("📦 Response: Should return 404 when application not found")
        @WithMockUser(roles = {"USER"})
        void shouldReturn404WhenNotFound() throws Exception {
            when(applicationService.findById("NONEXISTENT"))
                    .thenThrow(new ResourceNotFoundException("Application", "NONEXISTENT"));

            mockMvc.perform(get("/api/applications/{id}", "NONEXISTENT"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
                    .andExpect(jsonPath("$.message").exists())
                    .andExpect(jsonPath("$.path").value("/api/applications/NONEXISTENT"))
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("📦 Response: Should handle null libLab gracefully")
        @WithMockUser(roles = {"USER"})
        void shouldHandleNullLibLab() throws Exception {
            ApplicationDto dtoWithNullLibLab = new ApplicationDto();
            dtoWithNullLibLab.setCodApp("APP001");
            dtoWithNullLibLab.setLibApp("Test App");
            dtoWithNullLibLab.setLibLab(null);

            when(applicationService.findById("APP001")).thenReturn(dtoWithNullLibLab);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.data.codApp").value("APP001"))
                    .andExpect(jsonPath("$.data.libLab").doesNotExist());
        }

        @Test
        @DisplayName("🔗 Service: Should call findById with correct parameter")
        @WithMockUser(roles = {"USER"})
        void shouldCallFindByIdWithCorrectParam() throws Exception {
            when(applicationService.findById("APP001")).thenReturn(validApplicationDto);

            mockMvc.perform(get("/api/applications/{id}", "APP001"))
                    .andExpect(status().isOk());

            verify(applicationService, times(1)).findById("APP001");
            verifyNoMoreInteractions(applicationService);
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        POST /api/applications                                ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("POST /api/applications - Create Application")
    class CreateApplicationTests {

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("URL & Method Tests")
        class UrlAndMethodTests {

            @Test
            @DisplayName("✅ URL: Should map to POST /api/applications")
            @WithMockUser(roles = {"ADMIN"})
            void shouldMapCorrectUrl() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("✅ Method: Should only accept POST")
            @WithMockUser(roles = {"ADMIN"})
            void shouldOnlyAcceptPost() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());

                mockMvc.perform(put("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isMethodNotAllowed());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Security Tests")
        class SecurityTests {

            @Test
            @DisplayName("🔒 Should return 401 when not authenticated")
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("🔒 Should return 403 when USER role (not ADMIN)")
            @WithMockUser(roles = {"USER"})
            void shouldReturn403ForUserRole() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("🔒 Should return 403 when GUEST role")
            @WithMockUser(roles = {"GUEST"})
            void shouldReturn403ForGuestRole() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("🔒 Should allow ADMIN role")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAllowAdminRole() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("🔒 Should not call service when unauthorized")
            void shouldNotCallServiceWhenUnauthorized() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isUnauthorized());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("🔒 Should not call service when forbidden")
            @WithMockUser(roles = {"USER"})
            void shouldNotCallServiceWhenForbidden() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isForbidden());

                verifyNoInteractions(applicationService);
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Validation Tests - @Valid")
        class ValidationTests {

            // ─────────────────── codApp Validation ───────────────────

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp is null")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenCodAppIsNull() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp(null);
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(400))
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.message").value("Validation failed"))
                        .andExpect(jsonPath("$.fieldErrors").isArray())
                        .andExpect(jsonPath("$.fieldErrors[?(@.field=='codApp')]").exists());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp is empty")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenCodAppIsEmpty() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("");
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.fieldErrors[?(@.field=='codApp')]").exists());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp is blank (only spaces)")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenCodAppIsBlank() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("   ");
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp exceeds 50 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenCodAppTooLong() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("A".repeat(51));
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.fieldErrors[?(@.field=='codApp')]").exists());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("✅ Validation: Should accept codApp with exactly 50 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptCodAppWith50Characters() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("A".repeat(50));
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("✅ Validation: Should accept codApp with 1 character")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptCodAppWith1Character() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("A");
                dto.setLibApp("Test App");
                dto.setLibLab("Test Label");

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            // ─────────────────── libApp Validation ───────────────────

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libApp is null")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenLibAppIsNull() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp(null);
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.fieldErrors[?(@.field=='libApp')]").exists());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libApp is empty")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenLibAppIsEmpty() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libApp is blank (only spaces)")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenLibAppIsBlank() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("     ");
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libApp exceeds 100 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenLibAppTooLong() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("A".repeat(101));
                dto.setLibLab("Test Label");

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.fieldErrors[?(@.field=='libApp')]").exists());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("✅ Validation: Should accept libApp with exactly 100 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptLibAppWith100Characters() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("A".repeat(100));
                dto.setLibLab("Test Label");

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            // ─────────────────── libLab Validation ───────────────────

            @Test
            @DisplayName("✅ Validation: Should accept null libLab (optional field)")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptNullLibLab() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("Test App");
                dto.setLibLab(null);

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("✅ Validation: Should accept empty libLab")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptEmptyLibLab() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("Test App");
                dto.setLibLab("");

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libLab exceeds 100 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenLibLabTooLong() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("Test App");
                dto.setLibLab("A".repeat(101));

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("✅ Validation: Should accept libLab with exactly 100 characters")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAcceptLibLabWith100Characters() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp("Test App");
                dto.setLibLab("A".repeat(100));

                when(applicationService.create(any())).thenReturn(dto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            // ─────────────────── Multiple Validation Errors ───────────────────

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when multiple fields are invalid")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenMultipleFieldsInvalid() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp(null);
                dto.setLibApp("");
                dto.setLibLab("A".repeat(101));

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                        .andExpect(jsonPath("$.fieldErrors").isArray())
                        .andExpect(jsonPath("$.fieldErrors", hasSize(greaterThanOrEqualTo(2))));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when all required fields are missing")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenAllRequiredFieldsMissing() throws Exception {
                ApplicationDto dto = new ApplicationDto();

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            // ─────────────────── JSON Format Errors ───────────────────

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when JSON is malformed")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenJsonMalformed() throws Exception {
                String jsonText= "{ invalid json }";
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonText))
                        .andExpect(status().isBadRequest());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when request body is missing")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn400WhenBodyMissing() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 415 when content type is wrong")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn415WhenWrongContentType() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.TEXT_PLAIN)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isUnsupportedMediaType());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 415 when content type is XML")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn415WhenXmlContentType() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_XML)
                                .content("<applicationDto><codApp>APP001</codApp></applicationDto>"))
                        .andExpect(status().isUnsupportedMediaType());

                verifyNoInteractions(applicationService);
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Response Tests")
        class ResponseTests {

            @Test
            @DisplayName("📦 Response: Should return 201 Created")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn201() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("📦 Response: Should return correct response structure")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturnCorrectStructure() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.returnCode").value(0))
                        .andExpect(jsonPath("$.message").value(Constants.CREATED))
                        .andExpect(jsonPath("$.data.codApp").value("APP001"))
                        .andExpect(jsonPath("$.data.libApp").value("Test Application"))
                        .andExpect(jsonPath("$.data.libLab").value("Test Label"));
            }

            @Test
            @DisplayName("📦 Response: Should return saved data from service")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturnSavedData() throws Exception {
                ApplicationDto savedDto = new ApplicationDto();
                savedDto.setCodApp("APP001");
                savedDto.setLibApp("Saved Application");
                savedDto.setLibLab("Saved Label");

                when(applicationService.create(any())).thenReturn(savedDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.returnCode").value(0))
                        .andExpect(jsonPath("$.data.libApp").value("Saved Application"));
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Service Interaction Tests")
        class ServiceInteractionTests {

            @Test
            @DisplayName("🔗 Service: Should call create() exactly once")
            @WithMockUser(roles = {"ADMIN"})
            void shouldCallCreateOnce() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());

                verify(applicationService, times(1)).create(any(ApplicationDto.class));
                verifyNoMoreInteractions(applicationService);
            }

            @Test
            @DisplayName("🔗 Service: Should pass correct DTO to service")
            @WithMockUser(roles = {"ADMIN"})
            void shouldPassCorrectDtoToService() throws Exception {
                ArgumentCaptor<ApplicationDto> captor = ArgumentCaptor.forClass(ApplicationDto.class);
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());

                verify(applicationService).create(captor.capture());
                ApplicationDto captured = captor.getValue();

                assertThat(captured.getCodApp()).isEqualTo("APP001");
                assertThat(captured.getLibApp()).isEqualTo("Test Application");
                assertThat(captured.getLibLab()).isEqualTo("Test Label");
            }

            @Test
            @DisplayName("🔗 Service: Should not call other service methods")
            @WithMockUser(roles = {"ADMIN"})
            void shouldNotCallOtherMethods() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);

                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());

                verify(applicationService, never()).findAll();
                verify(applicationService, never()).findById(any());
                verify(applicationService, never()).update(any(), any());
                verify(applicationService, never()).delete(any());
            }
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        PUT /api/applications/{id}                            ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("PUT /api/applications/{id} - Update Application")
    class UpdateApplicationTests {

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("URL & Method Tests")
        class UrlAndMethodTests {

            @Test
            @DisplayName("✅ URL: Should map to PUT /api/applications/{id}")
            @WithMockUser(roles = {"USER"})
            void shouldMapCorrectUrl() throws Exception {
                when(applicationService.update(eq("APP001"), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ Method: Should not accept PATCH")
            @WithMockUser(roles = {"USER"})
            void shouldNotAcceptPatch() throws Exception {
                mockMvc.perform(patch("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isMethodNotAllowed());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Security Tests")
        class SecurityTests {

            @Test
            @DisplayName("🔒 Should return 401 when not authenticated")
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("🔒 Should allow USER role")
            @WithMockUser(roles = {"USER"})
            void shouldAllowUserRole() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("🔒 Should allow ADMIN role")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAllowAdminRole() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Validation Tests")
        class ValidationTests {

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp is null")
            @WithMockUser(roles = {"USER"})
            void shouldReturn400WhenCodAppIsNull() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp(null);
                dto.setLibApp("Test App");

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp is blank")
            @WithMockUser(roles = {"USER"})
            void shouldReturn400WhenCodAppIsBlank() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("   ");
                dto.setLibApp("Test App");

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when libApp is null")
            @WithMockUser(roles = {"USER"})
            void shouldReturn400WhenLibAppIsNull() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("APP001");
                dto.setLibApp(null);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when codApp too long")
            @WithMockUser(roles = {"USER"})
            void shouldReturn400WhenCodAppTooLong() throws Exception {
                ApplicationDto dto = new ApplicationDto();
                dto.setCodApp("A".repeat(51));
                dto.setLibApp("Test App");

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("⚠️ Validation: Should return 400 when JSON malformed")
            @WithMockUser(roles = {"USER"})
            void shouldReturn400WhenJsonMalformed() throws Exception {
                String textTest= "{ bad json }";
                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(textTest))
                        .andExpect(status().isBadRequest());

                verifyNoInteractions(applicationService);
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Response Tests")
        class ResponseTests {

            @Test
            @DisplayName("📦 Response: Should return 200 OK")
            @WithMockUser(roles = {"USER"})
            void shouldReturn200() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("📦 Response: Should return correct response structure")
            @WithMockUser(roles = {"USER"})
            void shouldReturnCorrectStructure() throws Exception {
                ApplicationDto updatedDto = new ApplicationDto();
                updatedDto.setCodApp("APP001");
                updatedDto.setLibApp("Updated App");
                updatedDto.setLibLab("Updated Label");

                when(applicationService.update(eq("APP001"), any())).thenReturn(updatedDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.returnCode").value(0))
                        .andExpect(jsonPath("$.message").value(Constants.UPDATED))
                        .andExpect(jsonPath("$.data.codApp").value("APP001"))
                        .andExpect(jsonPath("$.data.libApp").value("Updated App"))
                        .andExpect(jsonPath("$.data.libLab").value("Updated Label"));
            }

            @Test
            @DisplayName("📦 Response: Should return 404 when application not found")
            @WithMockUser(roles = {"USER"})
            void shouldReturn404WhenNotFound() throws Exception {
                when(applicationService.update(eq("NONEXISTENT"), any()))
                        .thenThrow(new ResourceNotFoundException("Application", "NONEXISTENT"));

                mockMvc.perform(put("/api/applications/{id}", "NONEXISTENT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.status").value(404))
                        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"));
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Service Interaction Tests")
        class ServiceInteractionTests {

            @Test
            @DisplayName("🔗 Service: Should call update with correct parameters")
            @WithMockUser(roles = {"USER"})
            void shouldCallUpdateWithCorrectParams() throws Exception {
                ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
                ArgumentCaptor<ApplicationDto> dtoCaptor = ArgumentCaptor.forClass(ApplicationDto.class);

                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());

                verify(applicationService).update(idCaptor.capture(), dtoCaptor.capture());

                assertThat(idCaptor.getValue()).isEqualTo("APP001");
                assertThat(dtoCaptor.getValue().getCodApp()).isEqualTo("APP001");
                assertThat(dtoCaptor.getValue().getLibApp()).isEqualTo("Test Application");
            }

            @Test
            @DisplayName("🔗 Service: Should call update exactly once")
            @WithMockUser(roles = {"USER"})
            void shouldCallUpdateOnce() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);

                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());

                verify(applicationService, times(1)).update(any(), any());
                verifyNoMoreInteractions(applicationService);
            }
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        DELETE /api/applications/{id}                         ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("DELETE /api/applications/{id} - Delete Application")
    class DeleteApplicationTests {

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("URL & Method Tests")
        class UrlAndMethodTests {

            @Test
            @DisplayName("✅ URL: Should map to DELETE /api/applications/{id}")
            @WithMockUser(roles = {"ADMIN"})
            void shouldMapCorrectUrl() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ URL: Should handle different path variable values")
            @WithMockUser(roles = {"ADMIN"})
            void shouldHandleDifferentPathVariables() throws Exception {
                doNothing().when(applicationService).delete("MY-APP-123");

                mockMvc.perform(delete("/api/applications/{id}", "MY-APP-123"))
                        .andExpect(status().isOk());

                verify(applicationService).delete("MY-APP-123");
            }

            @Test
            @DisplayName("❌ Method: Should not allow DELETE on collection")
            @WithMockUser(roles = {"ADMIN"})
            void shouldNotAllowDeleteOnCollection() throws Exception {
                mockMvc.perform(delete("/api/applications"))
                        .andExpect(status().isMethodNotAllowed());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Security Tests")
        class SecurityTests {

            @Test
            @DisplayName("🔒 Should return 401 when not authenticated")
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("🔒 Should return 403 when USER role")
            @WithMockUser(roles = {"USER"})
            void shouldReturn403ForUserRole() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("🔒 Should return 403 when GUEST role")
            @WithMockUser(roles = {"GUEST"})
            void shouldReturn403ForGuestRole() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("🔒 Should allow ADMIN role")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAllowAdminRole() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("🔒 Should not call service when unauthorized")
            void shouldNotCallServiceWhenUnauthorized() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isUnauthorized());

                verifyNoInteractions(applicationService);
            }

            @Test
            @DisplayName("🔒 Should not call service when forbidden")
            @WithMockUser(roles = {"USER"})
            void shouldNotCallServiceWhenForbidden() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isForbidden());

                verifyNoInteractions(applicationService);
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Response Tests")
        class ResponseTests {

            @Test
            @DisplayName("📦 Response: Should return 200 OK")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn200() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("📦 Response: Should return correct response structure")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturnCorrectStructure() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.returnCode").value(0))
                        .andExpect(jsonPath("$.message").value(Constants.SUCCES))
                        .andExpect(jsonPath("$.data").value("Supprimé avec succès"));
            }

            @Test
            @DisplayName("📦 Response: Should return 404 when application not found")
            @WithMockUser(roles = {"ADMIN"})
            void shouldReturn404WhenNotFound() throws Exception {
                doThrow(new ResourceNotFoundException("Application", "NONEXISTENT"))
                        .when(applicationService).delete("NONEXISTENT");

                mockMvc.perform(delete("/api/applications/{id}", "NONEXISTENT"))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.status").value(404))
                        .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"));
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Service Interaction Tests")
        class ServiceInteractionTests {

            @Test
            @DisplayName("🔗 Service: Should call delete with correct parameter")
            @WithMockUser(roles = {"ADMIN"})
            void shouldCallDeleteWithCorrectParam() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk());

                verify(applicationService, times(1)).delete("APP001");
            }

            @Test
            @DisplayName("🔗 Service: Should not call other methods")
            @WithMockUser(roles = {"ADMIN"})
            void shouldNotCallOtherMethods() throws Exception {
                doNothing().when(applicationService).delete("APP001");

                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isOk());

                verify(applicationService, never()).findAll();
                verify(applicationService, never()).findById(any());
                verify(applicationService, never()).create(any());
                verify(applicationService, never()).update(any(), any());
            }
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        POST /api/applications/add                            ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("POST /api/applications/add - Create Application With Profiles")
    class CreateWithProfilesTests {

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("URL & Method Tests")
        class UrlAndMethodTests {

            @Test
            @DisplayName("✅ URL: Should map to POST /api/applications/add")
            @WithMockUser(roles = {"USER"})
            void shouldMapCorrectUrl() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Security Tests")
        class SecurityTests {

            @Test
            @DisplayName("🔒 Should return 401 when not authenticated")
            void shouldReturn401WhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("🔒 Should allow USER role")
            @WithMockUser(roles = {"USER"})
            void shouldAllowUserRole() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("🔒 Should allow ADMIN role")
            @WithMockUser(roles = {"ADMIN"})
            void shouldAllowAdminRole() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Response Tests")
        class ResponseTests {

            @Test
            @DisplayName("📦 Response: Should return 201 Created")
            @WithMockUser(roles = {"USER"})
            void shouldReturn201() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("📦 Response: Should return correct response structure")
            @WithMockUser(roles = {"USER"})
            void shouldReturnCorrectStructure() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.returnCode").value(0))
                        .andExpect(jsonPath("$.message").value(Constants.CREATED))
                        .andExpect(jsonPath("$.data").exists());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("Service Interaction Tests")
        class ServiceInteractionTests {

            @Test
            @DisplayName("🔗 Service: Should call both create and createProfilesForApplication")
            @WithMockUser(roles = {"USER"})
            void shouldCallBothMethods() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());

                verify(applicationService, times(1)).create(any(ApplicationDto.class));
                verify(applicationService, times(1)).createProfilesForApplication(any(AddApplicationDto.class));
            }

            @Test
            @DisplayName("🔗 Service: Should call create before createProfilesForApplication")
            @WithMockUser(roles = {"USER"})
            void shouldCallInCorrectOrder() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());

                var inOrder = inOrder(applicationService);
                inOrder.verify(applicationService).create(any(ApplicationDto.class));
                inOrder.verify(applicationService).createProfilesForApplication(any(AddApplicationDto.class));
            }
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        GET /api/applications/authorized                      ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("GET /api/applications/authorized - Get Authorized Applications")
    class GetAuthorizedApplicationsTests {

        @Test
        @DisplayName("✅ URL: Should map to GET /api/applications/authorized")
        @WithMockUser(roles = {"USER"})
        void shouldMapCorrectUrl() throws Exception {
            when(applicationService.getAllAuthorizedApplications()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications/authorized"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("🔒 Security: Should return 401 when not authenticated")
        void shouldReturn401WhenNotAuthenticated() throws Exception {
            mockMvc.perform(get("/api/applications/authorized"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("🔒 Security: Should allow USER role")
        @WithMockUser(roles = {"USER"})
        void shouldAllowUserRole() throws Exception {
            when(applicationService.getAllAuthorizedApplications()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications/authorized"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("📦 Response: Should return 200 with authorized applications")
        @WithMockUser(roles = {"USER"})
        void shouldReturn200WithApps() throws Exception {
            when(applicationService.getAllAuthorizedApplications())
                    .thenReturn(List.of(validApplicationDto));

            mockMvc.perform(get("/api/applications/authorized"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.returnCode").value(0))
                    .andExpect(jsonPath("$.message").value(Constants.SUCCES))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data[0].codApp").value("APP001"));
        }

        @Test
        @DisplayName("🔗 Service: Should call getAllAuthorizedApplications")
        @WithMockUser(roles = {"USER"})
        void shouldCallService() throws Exception {
            when(applicationService.getAllAuthorizedApplications()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/applications/authorized"))
                    .andExpect(status().isOk());

            verify(applicationService, times(1)).getAllAuthorizedApplications();
            verifyNoMoreInteractions(applicationService);
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        WRONG HTTP METHOD TESTS                               ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("Wrong HTTP Method Tests - Should Return 405")
    class WrongHttpMethodTests {

        @Test
        @DisplayName("❌ PUT on /api/applications should return 405")
        @WithMockUser(roles = {"ADMIN"})
        void putOnCollectionShouldReturn405() throws Exception {
            mockMvc.perform(put("/api/applications")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validApplicationDto)))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("❌ DELETE on /api/applications should return 405")
        @WithMockUser(roles = {"ADMIN"})
        void deleteOnCollectionShouldReturn405() throws Exception {
            mockMvc.perform(delete("/api/applications"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("❌ PATCH on /api/applications/{id} should return 405")
        @WithMockUser(roles = {"ADMIN"})
        void patchOnResourceShouldReturn405() throws Exception {
            mockMvc.perform(patch("/api/applications/{id}", "APP001")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validApplicationDto)))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("❌ POST on /api/applications/{id} should return 405")
        @WithMockUser(roles = {"ADMIN"})
        void postOnResourceShouldReturn405() throws Exception {
            mockMvc.perform(post("/api/applications/{id}", "APP001")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validApplicationDto)))
                    .andExpect(status().isMethodNotAllowed());
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        ROLE-BASED ACCESS CONTROL MATRIX                      ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("Role-Based Access Control Matrix")
    class RoleBasedAccessControlTests {

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("USER Role Permissions")
        class UserRoleTests {

            @Test
            @DisplayName("✅ USER can GET /api/applications")
            @WithMockUser(roles = {"USER"})
            void userCanGetAll() throws Exception {
                when(applicationService.findAll()).thenReturn(Collections.emptyList());
                mockMvc.perform(get("/api/applications")).andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ USER can GET /api/applications/{id}")
            @WithMockUser(roles = {"USER"})
            void userCanGetById() throws Exception {
                when(applicationService.findById("APP001")).thenReturn(validApplicationDto);
                mockMvc.perform(get("/api/applications/{id}", "APP001")).andExpect(status().isOk());
            }

            @Test
            @DisplayName("❌ USER cannot POST /api/applications")
            @WithMockUser(roles = {"USER"})
            void userCannotPost() throws Exception {
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("✅ USER can PUT /api/applications/{id}")
            @WithMockUser(roles = {"USER"})
            void userCanPut() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);
                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("❌ USER cannot DELETE /api/applications/{id}")
            @WithMockUser(roles = {"USER"})
            void userCannotDelete() throws Exception {
                mockMvc.perform(delete("/api/applications/{id}", "APP001"))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("✅ USER can POST /api/applications/add")
            @WithMockUser(roles = {"USER"})
            void userCanPostAdd() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                doNothing().when(applicationService).createProfilesForApplication(any());

                mockMvc.perform(post("/api/applications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validAddApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("✅ USER can GET /api/applications/authorized")
            @WithMockUser(roles = {"USER"})
            void userCanGetAuthorized() throws Exception {
                when(applicationService.getAllAuthorizedApplications()).thenReturn(Collections.emptyList());
                mockMvc.perform(get("/api/applications/authorized")).andExpect(status().isOk());
            }
        }

        @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
        @DisplayName("ADMIN Role Permissions")
        class AdminRoleTests {

            @Test
            @DisplayName("✅ ADMIN can GET /api/applications")
            @WithMockUser(roles = {"ADMIN"})
            void adminCanGetAll() throws Exception {
                when(applicationService.findAll()).thenReturn(Collections.emptyList());
                mockMvc.perform(get("/api/applications")).andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ ADMIN can GET /api/applications/{id}")
            @WithMockUser(roles = {"ADMIN"})
            void adminCanGetById() throws Exception {
                when(applicationService.findById("APP001")).thenReturn(validApplicationDto);
                mockMvc.perform(get("/api/applications/{id}", "APP001")).andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ ADMIN can POST /api/applications")
            @WithMockUser(roles = {"ADMIN"})
            void adminCanPost() throws Exception {
                when(applicationService.create(any())).thenReturn(validApplicationDto);
                mockMvc.perform(post("/api/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("✅ ADMIN can PUT /api/applications/{id}")
            @WithMockUser(roles = {"ADMIN"})
            void adminCanPut() throws Exception {
                when(applicationService.update(any(), any())).thenReturn(validApplicationDto);
                mockMvc.perform(put("/api/applications/{id}", "APP001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validApplicationDto)))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("✅ ADMIN can DELETE /api/applications/{id}")
            @WithMockUser(roles = {"ADMIN"})
            void adminCanDelete() throws Exception {
                doNothing().when(applicationService).delete("APP001");
                mockMvc.perform(delete("/api/applications/{id}", "APP001")).andExpect(status().isOk());
            }
        }
    }

    // ╔══════════════════════════════════════════════════════════════════════════════╗
    // ║                                                                              ║
    // ║                        ERROR HANDLING TESTS                                  ║
    // ║                                                                              ║
    // ╚══════════════════════════════════════════════════════════════════════════════╝

    @Nested 
@WebMvcTest(
    controllers = ApplicationController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtRequestFilter.class
))
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("📦 Should return 500 when service throws unexpected exception")
        @WithMockUser(roles = {"USER"})
        void shouldReturn500OnUnexpectedException() throws Exception {
            when(applicationService.findAll())
                    .thenThrow(new RuntimeException("Database connection failed"));

            mockMvc.perform(get("/api/applications"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                    .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
        }

        @Test
        @DisplayName("📦 Should return 404 when resource not found")
        @WithMockUser(roles = {"USER"})
        void shouldReturn404OnResourceNotFound() throws Exception {
            when(applicationService.findById("UNKNOWN"))
                    .thenThrow(new ResourceNotFoundException("Application", "UNKNOWN"));

            mockMvc.perform(get("/api/applications/{id}", "UNKNOWN"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"));
        }

        @Test
        @DisplayName("📦 Should return validation errors with field details")
        @WithMockUser(roles = {"ADMIN"})
        void shouldReturnValidationErrorsWithFieldDetails() throws Exception {
            ApplicationDto invalidDto = new ApplicationDto();
            invalidDto.setCodApp(null);
            invalidDto.setLibApp(null);

            mockMvc.perform(post("/api/applications")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                    .andExpect(jsonPath("$.message").value("Validation failed"))
                    .andExpect(jsonPath("$.fieldErrors").isArray())
                    .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("codApp", "libApp")))
                    .andExpect(jsonPath("$.fieldErrors[*].message").exists());
        }
    }
}