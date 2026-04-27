package com.bna.habil.application.services;

import com.bna.habil.application.dto.ldap.*;
import java.util.List;
import java.util.Map;

public interface LdapService {

    Map<String, Object> testConnection();

    List<String> findAvailableOUs();

    List<Map<String, String>> findAllUsers();

    boolean testSearchBase(String base);

    Map<String, Object> getLdapStructure();

    UserResponse addUser(UserRequest request);

    UserDetailResponse updateUser(String matricule, UpdateUserRequest request);

    UserResponse getUserByMatricule(String matricule);

    UserDetailResponse getUserDetailByMatricule(String matricule);

    boolean userExists(String matricule);

    void deleteUser(String matricule);
}