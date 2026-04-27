package com.bna.habil.interfaces.controllers;

import com.bna.habil.application.dto.ldap.UpdateUserRequest;
import com.bna.habil.application.dto.ldap.UserDetailResponse;
import com.bna.habil.application.dto.ldap.UserRequest;
import com.bna.habil.application.dto.ldap.UserResponse;
import com.bna.habil.application.services.LdapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ldap")
@RequiredArgsConstructor
@Slf4j
public class LdapController {

    private final LdapService ldapService;

    // ==================== DIAGNOSTICS ====================

    @GetMapping("/test/connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> result = ldapService.testConnection();
        HttpStatus status = "SUCCESS".equals(result.get("status"))
                ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(result);
    }

    @GetMapping("/test/ous")
    public List<String> findOUs() {
        return ldapService.findAvailableOUs();
    }

    @GetMapping("/test/structure")
    public Map<String, Object> getLdapStructure() {
        return ldapService.getLdapStructure();
    }

    @GetMapping("/test/search-base")
    public Map<String, Object> testSearchBase(@RequestParam String base) {
        boolean exists = ldapService.testSearchBase(base);
        return Map.of(
                "searchBase", base,
                "exists", exists,
                "message", exists ? "Search base is valid" : "Search base does not exist"
        );
    }

    @GetMapping("/test/users")
    public List<Map<String, String>> findAllUsers() {
        return ldapService.findAllUsers();
    }

    // ==================== USER CRUD ====================

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return ldapService.addUser(request);
    }

    @PutMapping("/users/{matricule}")
    public UserDetailResponse updateUser(
            @PathVariable String matricule,
            @Valid @RequestBody UpdateUserRequest request) {
        return ldapService.updateUser(matricule, request);
    }

    @GetMapping("/users/{matricule}/exists")
    public boolean checkUserExists(@PathVariable String matricule) {
        return ldapService.userExists(matricule);
    }

    @GetMapping("/users/{matricule}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String matricule) {
        UserResponse user = ldapService.getUserByMatricule(matricule);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{matricule}/details")
    public ResponseEntity<UserDetailResponse> getUserDetails(@PathVariable String matricule) {
        UserDetailResponse user = ldapService.getUserDetailByMatricule(matricule);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{matricule}")
    public void deleteUser(@PathVariable String matricule) {
        ldapService.deleteUser(matricule);
    }
}