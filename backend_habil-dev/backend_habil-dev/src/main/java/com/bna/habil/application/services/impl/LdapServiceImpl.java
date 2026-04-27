package com.bna.habil.application.services.impl;

import com.bna.habil.application.dto.ldap.*;
import com.bna.habil.application.services.LdapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.*;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;

import jakarta.annotation.PostConstruct;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;

import javax.naming.directory.SearchControls;
import java.text.Normalizer;

@Service
@RequiredArgsConstructor
@Slf4j
public class LdapServiceImpl implements LdapService {

    private static final int PAGE_SIZE = 500;
    private static final String[] USER_OBJECT_CLASSES = {"top", "person", "organizationalPerson", "user"};
    private static final String USER_ACCOUNT_CONTROL_ENABLED = "512";
    private static final String DOMAIN_SUFFIX = "@bna.tn";
    private static final String OBJECT_CLASS = "objectClass";
    private static final String LDAP_BASE_KEY = "ldapBase";
    private static final String SAM_ACCOUNT_NAME = "sAMAccountName";
    private static final String DISPLAY_NAME = "displayName";
    private static final String DISTINGUISHED_NAME = "distinguishedName";
    private static final String EMPLOYEE_ID = "employeeID";
    private static final String GIVEN_NAME = "givenName";
    private static final String USER_PRINCIPAL_NAME = "userPrincipalName";
    private static final String STREET_ADDRESS = "streetAddress";
    private static final String DEPARTMENT = "department";
    private static final String DIVISION = "division";
    private static final String DESCRIPTION = "description";

    private final LdapTemplate ldapTemplate;

    @Value("${ldap.searchBase:}")
    private String searchBase;

    @Value("${spring.ldap.base}")
    private String ldapBase;

    // ==================== LIFECYCLE ====================

    @PostConstruct
    public void validateLdapConfiguration() {
        log.info("Validating LDAP Configuration — base: [{}], searchBase: [{}]", ldapBase, searchBase);
        try {
            testConnection();
            log.info("✓ LDAP connection successful");
        } catch (Exception e) {
            log.error("✗ LDAP connection failed: {}", e.getMessage());
        }
    }

    // ==================== DIAGNOSTICS ====================

    @Override
    public Map<String, Object> testConnection() {
        log.info("Testing LDAP connection...");
        try {
            var results = ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(OBJECT_CLASS).is("*"),
                    (ContextMapper<Object>) ctx -> ctx
            );
            return Map.of(
                    "status", "SUCCESS",
                    "message", "LDAP connection successful",
                    LDAP_BASE_KEY, ldapBase,
                    "searchBase", searchBase,
                    "resultsFound", results.size()
            );
        } catch (Exception e) {
            log.error("LDAP connection test failed", e);
            return Map.of(
                    "status", "FAILED",
                    "message", e.getMessage(),
                    LDAP_BASE_KEY, ldapBase,
                    "searchBase", searchBase
            );
        }
    }

    @Override
    public List<String> findAvailableOUs() {
        log.info("Searching for available OUs...");
        try {
            List<String> ous = ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(OBJECT_CLASS).is("organizationalUnit"),
                    new OUAttributesMapper()
            );
            log.info("Found {} organizational units", ous.size());
            return ous;
        } catch (Exception e) {
            log.error("Error finding organizational units", e);
            return List.of();
        }
    }

    @Override
    public List<Map<String, String>> findAllUsers() {
        log.info("Fetching all users with paged results...");
        List<Map<String, String>> allUsers = new ArrayList<>();

        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(new String[]{SAM_ACCOUNT_NAME, DISPLAY_NAME, DISTINGUISHED_NAME});

            PagedResultsCookie cookie = null;
            do {
                var processor = new PagedResultsDirContextProcessor(PAGE_SIZE, cookie);
                List<Map<String, String>> page = ldapTemplate.search(
                        "", "(objectClass=user)", controls,
                        (AttributesMapper<Map<String, String>>) attrs -> Map.of(
                                "matricule", nullSafe(attrs, SAM_ACCOUNT_NAME),
                                "name", nullSafe(attrs, DISPLAY_NAME),
                                "dn", nullSafe(attrs, DISTINGUISHED_NAME)
                        ),
                        processor
                );
                allUsers.addAll(page);
                cookie = processor.getCookie();
            } while (cookie != null && cookie.getCookie() != null);

            log.info("Total users found: {}", allUsers.size());
            return allUsers;
        } catch (Exception e) {
            log.error("Error fetching all users", e);
            return List.of();
        }
    }

    @Override
    public boolean testSearchBase(String testBase) {
        log.info("Testing search base: {}", testBase);
        try {
            var result = ldapTemplate.search(
                    LdapQueryBuilder.query().base(testBase).where(OBJECT_CLASS).is("*"),
                    (ContextMapper<Object>) ctx -> ctx
            );
            log.info("Search base '{}' valid — {} entries", testBase, result.size());
            return true;
        } catch (org.springframework.ldap.NameNotFoundException e) {
            log.warn("Search base '{}' does not exist", testBase);
            return false;
        } catch (Exception e) {
            log.error("Error testing search base '{}'", testBase, e);
            return false;
        }
    }

    @Override
    public Map<String, Object> getLdapStructure() {
        return Map.of(
                LDAP_BASE_KEY, ldapBase,
                "configuredSearchBase", searchBase,
                "searchBaseExists", testSearchBase(searchBase),
                "availableOUs", findAvailableOUs()
        );
    }

    // ==================== USER CRUD ====================

    @Override
    public UserResponse addUser(UserRequest request) {
        log.info("Creating user: {}", request.getMatricule());

        if (userExists(request.getMatricule())) {
            throw new RuntimeException(request.getMatricule());
        }

        try {
            String parentOu = resolveOU(request.getStructure());
            String fullName = request.getPrenom() + " " + request.getNom();

            Name dn = LdapNameBuilder.newInstance(parentOu).add("CN", request.getMatricule()).build();

            DirContextAdapter ctx = new DirContextAdapter(dn);
            ctx.setAttributeValues(OBJECT_CLASS, USER_OBJECT_CLASSES);
            ctx.setAttributeValue("cn", request.getMatricule());
            ctx.setAttributeValue(SAM_ACCOUNT_NAME, request.getMatricule());
            ctx.setAttributeValue(EMPLOYEE_ID, request.getCin());
            ctx.setAttributeValue(GIVEN_NAME, request.getPrenom());
            ctx.setAttributeValue("sn", request.getNom());
            ctx.setAttributeValue(DISPLAY_NAME, fullName);

            String upn = generateUniqueUpn(request.getPrenom(), request.getNom());
            ctx.setAttributeValue(USER_PRINCIPAL_NAME, upn);
            ctx.setAttributeValue("userAccountControl", USER_ACCOUNT_CONTROL_ENABLED);

            setIfPresent(ctx, "mail", request.getEmail());
            setIfPresent(ctx, STREET_ADDRESS, request.getAdresse());
            setIfPresent(ctx, DEPARTMENT, request.getStructure());
            setIfPresent(ctx, DIVISION, request.getDivision());
            setIfPresent(ctx, DESCRIPTION, request.getDescription());


            ldapTemplate.bind(ctx);
            log.info("User bound to LDAP: {}", request.getMatricule());

            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                setPassword(dn, request.getPassword());
            }

            return new UserResponse(
                    request.getMatricule(), request.getNom(), request.getPrenom(),
                    request.getEmail(), request.getStructure()
            );
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + request.getMatricule(), e);
        }
    }

    @Override
    public UserDetailResponse updateUser(String matricule, UpdateUserRequest request) {
        log.info("Updating user: {}", matricule);

        Name workingDn = resolveUserDn(matricule);

        try {
            if (request.getStructure() != null && !request.getStructure().isBlank()) {
                workingDn = moveUserIfOUChanged(workingDn, request.getStructure(), matricule);
            }

            List<ModificationItem> mods = buildModifications(matricule, request);
            if (!mods.isEmpty()) {
                ldapTemplate.modifyAttributes(workingDn, mods.toArray(new ModificationItem[0]));
                log.info("Attributes updated for: {}", matricule);
            }

            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                setPassword(workingDn, request.getPassword());
            }

            return getUserDetailByMatricule(matricule);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + matricule, e);
        }
    }

    @Override
    public UserResponse getUserByMatricule(String matricule) {
        log.info("Retrieving user: {}", matricule);
        try {
            List<UserResponse> users = ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(SAM_ACCOUNT_NAME).is(matricule),
                    new UserAttributesMapper()
            );
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + matricule, e);
        }
    }

    @Override
    public UserDetailResponse getUserDetailByMatricule(String matricule) {
        log.info("Retrieving user details: {}", matricule);
        try {
            List<UserDetailResponse> users = ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(SAM_ACCOUNT_NAME).is(matricule),
                    new UserDetailAttributesMapper()
            );
            return users.isEmpty() ? null : users.getFirst();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user details: " + matricule, e);
        }
    }

    @Override
    public boolean userExists(String matricule) {
        try {
            var result = ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(SAM_ACCOUNT_NAME).is(matricule),
                    (ContextMapper<Object>) ctx -> ctx
            );
            return !result.isEmpty();
        } catch (Exception e) {
            log.error("Error checking existence for: {}", matricule, e);
            return false;
        }
    }

    @Override
    public void deleteUser(String matricule) {
        log.info("Deleting user: {}", matricule);
        Name dn = resolveUserDn(matricule);

        try {
            ldapTemplate.unbind(dn);
            log.info("User deleted from LDAP: {}", matricule);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + matricule, e);
        }
    }

    // ==================== PRIVATE HELPERS ====================

    private Name resolveUserDn(String matricule) {
        if (!userExists(matricule)) {
            throw new RuntimeException(matricule);
        }
        List<Name> dns = ldapTemplate.search(
                LdapQueryBuilder.query().base("").where(SAM_ACCOUNT_NAME).is(matricule),
                (ContextMapper<Name>) ctx -> ((DirContextAdapter) ctx).getDn()
        );
        if (dns.isEmpty()) {
            throw new RuntimeException(matricule);
        }
        return dns.getFirst();
    }

    private Name moveUserIfOUChanged(Name currentDn, String structure, String matricule) {
        String currentOU = currentDn.toString();
        currentOU = currentOU.contains(",")
                ? currentOU.substring(currentOU.indexOf(",") + 1) : "";

        String targetOU = resolveOU(structure);

        if (currentOU.equalsIgnoreCase(targetOU)) {
            return currentDn;
        }

        log.info("Moving user '{}': [{}] → [{}]", matricule, currentOU, targetOU);

        Name newDn = LdapNameBuilder.newInstance(targetOU).add("CN", matricule).build();
        ldapTemplate.rename(currentDn, newDn);
        log.info("User moved successfully");
        return newDn;
    }

    private String extractCnValue(String dnString) {
        String cnRdn = dnString.contains(",")
                ? dnString.substring(0, dnString.indexOf(",")) : dnString;
        return cnRdn.contains("=") ? cnRdn.substring(cnRdn.indexOf("=") + 1) : cnRdn;
    }

    private List<ModificationItem> buildModifications(String matricule, UpdateUserRequest request) {
        List<ModificationItem> mods = new ArrayList<>();

        BiConsumer<String, String> addMod = (attr, value) -> {
            if (value != null && !value.isBlank()) {
                mods.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(attr, value)));
            }
        };

        if (request.getPrenom() != null || request.getNom() != null) {
            UserDetailResponse current = getUserDetailByMatricule(matricule);
            String prenom = request.getPrenom() != null ? request.getPrenom() : current.getPrenom();
            String nom = request.getNom() != null ? request.getNom() : current.getNom();
            String fullName = prenom + " " + nom;

            addMod.accept(GIVEN_NAME, prenom);
            addMod.accept("sn", nom);
            addMod.accept("cn", fullName);
            addMod.accept(DISPLAY_NAME, fullName);
        }

        addMod.accept("mail", request.getEmail());
        addMod.accept(EMPLOYEE_ID, request.getCin());
        addMod.accept(STREET_ADDRESS, request.getAdresse());
        addMod.accept(DEPARTMENT, request.getStructure());
        addMod.accept(DIVISION, request.getDivision());
        addMod.accept(DESCRIPTION, request.getDescription());

        return mods;
    }

    private String resolveOU(String structure) {
        if (structure == null || structure.isBlank()) {
            throw new IllegalArgumentException("Structure cannot be null or blank");
        }

        try {
            List<String> matchingOUs = ldapTemplate.search(
                    LdapQueryBuilder.query().base("")
                            .where(OBJECT_CLASS).is("organizationalUnit")
                            .and("ou").is(structure),
                    (AttributesMapper<String>) attrs -> {
                        var dn = attrs.get(DISTINGUISHED_NAME);
                        return dn != null ? (String) dn.get() : null;
                    }
            );

            List<String> validOUs = matchingOUs.stream()
                    .filter(ou -> ou != null && !ou.isBlank())
                    .toList();

            if (validOUs.isEmpty()) {
                throw new RuntimeException(structure);
            }

            if (validOUs.size() > 1) {
                log.warn("Multiple OUs for '{}', using first: {}", structure, validOUs.get(0));
            }

            return stripLdapBase(validOUs.get(0));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve OU for: " + structure, e);
        }
    }

    private void setPassword(Name dn, String password) {
        try {
            byte[] bytes = ("\"" + password + "\"").getBytes(StandardCharsets.UTF_16LE);
            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", bytes))
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to set password for DN: " + dn, e);
        }
    }

    private void setIfPresent(DirContextAdapter context, String attr, String value) {
        if (value != null && !value.isBlank()) {
            context.setAttributeValue(attr, value);
        }
    }

    private String stripLdapBase(String fullDn) {
        if (ldapBase != null && !ldapBase.isBlank()) {
            String suffix = "," + ldapBase;
            if (fullDn.toLowerCase().endsWith(suffix.toLowerCase())) {
                return fullDn.substring(0, fullDn.length() - suffix.length());
            }
        }
        return fullDn;
    }

    private static String nullSafe(Attributes attrs, String name) throws NamingException {
        var attr = attrs.get(name);
        return attr != null ? (String) attr.get() : "";
    }

    private static String getAttributeValue(Attributes attrs, String name) throws NamingException {
        var attr = attrs.get(name);
        return attr != null ? (String) attr.get() : null;
    }

    private String generateUniqueUpn(String prenom, String nom) {
        String base = sanitize(prenom) + "." + sanitize(nom);
        int suffix = 0;

        String upn;
        do {
            upn = base + (suffix == 0 ? "" : suffix) + DOMAIN_SUFFIX;
            suffix++;
        } while (upnExists(upn));

        return upn;
    }

    private boolean upnExists(String upn) {
        try {
            return !ldapTemplate.search(
                    LdapQueryBuilder.query().base("").where(USER_PRINCIPAL_NAME).is(upn),
                    (ContextMapper<Object>) ctx -> ctx
            ).isEmpty();
        } catch (Exception e) {
            log.error("Error checking UPN: {}", upn, e);
            return false;
        }
    }

    private String sanitize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("[\\p{M}]", "")   // é→e, à→a, etc.
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase();
    }

    // ==================== ATTRIBUTE MAPPERS ====================

    private static class OUAttributesMapper implements AttributesMapper<String> {
        @Override
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            var dn = attrs.get(DISTINGUISHED_NAME);
            if (dn != null) return (String) dn.get();
            var name = attrs.get("name");
            return name != null ? (String) name.get() : "Unknown";
        }
    }

    private static class UserAttributesMapper implements AttributesMapper<UserResponse> {
        @Override
        public UserResponse mapFromAttributes(Attributes attrs) throws NamingException {
            return new UserResponse(
                    getAttributeValue(attrs, SAM_ACCOUNT_NAME),
                    getAttributeValue(attrs, "sn"),
                    getAttributeValue(attrs, GIVEN_NAME),
                    getAttributeValue(attrs, "mail"),
                    getAttributeValue(attrs, DEPARTMENT)
            );
        }
    }

    private static class UserDetailAttributesMapper implements AttributesMapper<UserDetailResponse> {
        @Override
        public UserDetailResponse mapFromAttributes(Attributes attrs) throws NamingException {
            return new UserDetailResponse(
                    getAttributeValue(attrs, SAM_ACCOUNT_NAME),
                    getAttributeValue(attrs, EMPLOYEE_ID),
                    getAttributeValue(attrs, "sn"),
                    getAttributeValue(attrs, GIVEN_NAME),
                    getAttributeValue(attrs, DISPLAY_NAME),
                    getAttributeValue(attrs, "mail"),
                    getAttributeValue(attrs, DEPARTMENT),
                    getAttributeValue(attrs, DIVISION),
                    getAttributeValue(attrs, STREET_ADDRESS),
                    getAttributeValue(attrs, DESCRIPTION),
                    getAttributeValue(attrs, USER_PRINCIPAL_NAME),
                    getAttributeValue(attrs, "cn")
            );
        }
    }
}