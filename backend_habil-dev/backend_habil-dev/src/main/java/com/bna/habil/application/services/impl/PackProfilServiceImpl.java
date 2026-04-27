package com.bna.habil.application.services.impl;

import com.bna.habil.application.dto.PackProfilDto;
import com.bna.habil.application.dto.ProfileConflictDto;
import com.bna.habil.application.mappers.PackProfilMapper;
import com.bna.habil.application.services.PackProfilService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.*;
import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import com.bna.habil.domain.entities.entitiesId.UtilisateurProfilId;
import com.bna.habil.infrastructure.persistence.repositories.StructureCustomRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.*;
import com.bna.habil.interfaces.response.SyncResult;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PackProfilServiceImpl extends AbstractCrudService<PackProfil, PackProfilDto, PackProfilId>
        implements PackProfilService {

    private final PackProfilRepository packProfilRepository;
    private final PackRepository packRepository;
    private final ProfilRepository profilRepository;
    private final PackProfilMapper packProfilMapper;
    private final StructureCustomRepository structureRepository;
    private final UtilisateurProfilRepository utilisateurProfilRepository;
    private final UtilisateurPackRepository utilisateurPackRepository;
    private static final String ERROR_PACK_CODE_EMPTY = "Pack code cannot be empty";
    private static final String ERROR_PACK_PROFIL_NOT_FOUND = "PackProfil not found: Pack=%s, Profile=%s";

    public PackProfilServiceImpl(@Qualifier("packProfilRepository") PackProfilRepository repository,
                                 PackProfilMapper mapper,
                                 PackRepository packRepository,
                                 ProfilRepository profilRepository,
                                 StructureCustomRepository structureRepository,
                                 UtilisateurProfilRepository utilisateurProfilRepository,
                                 UtilisateurPackRepository utilisateurPackRepository) {
        super(
                repository,
                mapper,
                // ID Extractor
                dto -> new PackProfilId(dto.getCodPackPack(), dto.getCodPflPfl()),
                // ID Stringifier
                id -> String.format("Pack=%s,Profile=%s", id.getCodPackPack(), id.getCodPflPfl()),
                // Create Validator
                dto -> validateCreate(dto, packRepository, profilRepository),
                // Update Validator
                PackProfilServiceImpl::validateUpdate
        );
        this.packProfilRepository = repository;
        this.packRepository = packRepository;
        this.profilRepository = profilRepository;
        this.packProfilMapper = mapper;
        this.structureRepository = structureRepository;
        this.utilisateurProfilRepository = utilisateurProfilRepository;
        this.utilisateurPackRepository = utilisateurPackRepository;
    }

    // Static validation methods
    private static void validateCreate(PackProfilDto dto,
                                       PackRepository packRepository,
                                       ProfilRepository profilRepository) {
        validatePackCode(dto.getCodPackPack());
        validateProfileCode(dto.getCodPflPfl());
        validateStructureTypeCode(dto.getCodTstrcTstrc());
        verifyPackExists(dto.getCodPackPack(), packRepository);
        verifyProfileExists(dto.getCodPflPfl(), profilRepository);
    }

    private static void validatePackCode(String packCode) {
        if (packCode == null || packCode.trim().isEmpty()) {
            throw new ValidationException(ERROR_PACK_CODE_EMPTY);
        }
    }

    private static void validateProfileCode(String profileCode) {
        if (profileCode == null || profileCode.trim().isEmpty()) {
            throw new ValidationException("Profile code cannot be empty");
        }
    }

    private static void validateStructureTypeCode(String structureTypeCode) {
        if (structureTypeCode == null || structureTypeCode.trim().isEmpty()) {
            throw new ValidationException("Structure type code cannot be empty");
        }
    }

    private static void verifyPackExists(String packCode, PackRepository packRepository) {
        if (!packRepository.existsById(packCode)) {
            throw new EntityNotFoundException(
                    String.format("Pack with code %s not found", packCode)
            );
        }
    }

    private static void verifyProfileExists(String profileCode, ProfilRepository profilRepository) {
        if (!profilRepository.existsById(profileCode)) {
            throw new EntityNotFoundException(
                    String.format("Profile with code %s not found", profileCode)
            );
        }
    }

    private static void validateUpdate(PackProfilId id, PackProfilDto dto, PackProfil existing) {
        log.debug("Validating update for PackProfil: {}", id);
        if (dto.getBoolEtat() != null && dto.getBoolEtat() < 0) {
            throw new ValidationException("Status must be non-negative");
        }
    }

    @Override
    protected String getEntityName() {
        return "PackProfil";
    }

    @Override
    protected Set<PackProfilId> findExistingIds(List<PackProfilId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptySet();
        }
        // For composite keys, check individually
        return ids.stream()
                .filter(repository::existsById)
                .collect(Collectors.toSet());
    }

    // ==================== BUSINESS METHODS ====================

    @Transactional(readOnly = true)
    @Override
    public List<PackProfilDto> getPackProfilListByPack(String codPackPack) throws ValidationException {
        log.info("getPackProfilListByPack: input: codPackPack = {}", codPackPack);

        if (codPackPack == null || codPackPack.trim().isEmpty()) {
            throw new ValidationException(ERROR_PACK_CODE_EMPTY);
        }

        List<PackProfil> packProfilList = packProfilRepository.findByCodPackPack(codPackPack);

        log.info("getPackProfilListByPack: output: {} records found", packProfilList.size());

        return packProfilMapper.toDtoList(packProfilList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PackProfilDto> getPackProfilListByProfile(String codPflPfl) throws ValidationException {
        log.info("getPackProfilListByProfile: input: codPflPfl = {}", codPflPfl);

        if (codPflPfl == null || codPflPfl.trim().isEmpty()) {
            throw new ValidationException("Profile code cannot be empty");
        }

        List<PackProfil> packProfilList = packProfilRepository.findByCodPflPfl(codPflPfl);

        log.info("getPackProfilListByProfile: output: {} records found", packProfilList.size());

        return packProfilMapper.toDtoList(packProfilList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PackProfilDto> getActivePackProfilsByPack(String codPackPack) throws ValidationException {
        log.info("getActivePackProfilsByPack: input: codPackPack = {}", codPackPack);

        if (codPackPack == null || codPackPack.trim().isEmpty()) {
            throw new ValidationException(ERROR_PACK_CODE_EMPTY);
        }

        List<PackProfil> packProfilList = packProfilRepository.findByCodPackPackAndBoolEtat(codPackPack, 1);

        log.info("getActivePackProfilsByPack: output: {} active records found", packProfilList.size());

        return packProfilMapper.toDtoList(packProfilList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PackProfilDto> getPackProfilsByStructureType(String codPackPack, String codTstrcTstrc) throws ValidationException {
        log.info("getPackProfilsByStructureType: pack={}, structureType={}", codPackPack, codTstrcTstrc);

        if (codPackPack == null || codPackPack.trim().isEmpty()) {
            throw new ValidationException(ERROR_PACK_CODE_EMPTY);
        }
        if (codTstrcTstrc == null || codTstrcTstrc.trim().isEmpty()) {
            throw new ValidationException("Structure type code cannot be empty");
        }

        List<PackProfil> packProfilList = packProfilRepository
                .findByCodPackPackAndCodTstrcTstrc(codPackPack, codTstrcTstrc);

        log.info("getPackProfilsByStructureType: output: {} records found", packProfilList.size());

        return packProfilMapper.toDtoList(packProfilList);
    }

    @Transactional
    @Override
    public PackProfilDto addPackProfilList(List<PackProfilDto> listPackProfilDto) throws IllegalStateException {
        log.info("addPackProfilList: adding {} records", listPackProfilDto.size());

        List<PackProfil> packProfils = packProfilMapper.toEntityList(listPackProfilDto);
        log.info("addPackProfilList packProfils: {}", packProfils);

        // Check if any record already exists
        for (PackProfil pp : packProfils) {
            PackProfilId id = new PackProfilId(pp.getCodPackPack(), pp.getCodPflPfl());

            Optional<PackProfil> existing = packProfilRepository.findById(id);
            if (existing.isPresent()) {
                log.error("Record already exists: {}", id);
                throw new IllegalStateException("Record already exists with id: " + id);
            }
        }

        List<PackProfil> savedRecords = packProfilRepository.saveAll(packProfils);
        log.info("Successfully saved {} records", savedRecords.size());

        return null;
    }

    @Transactional
    @Override
    public void updatePackProfilStatus(String codPackPack, String codPflPfl, Integer boolEtat) {
        log.info("updatePackProfilStatus: pack={}, profile={}, status={}",
                codPackPack, codPflPfl, boolEtat);

        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        PackProfil packProfil = packProfilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_PACK_PROFIL_NOT_FOUND, codPackPack, codPflPfl)
                ));

        packProfil.setBoolEtat(boolEtat);
        packProfilRepository.save(packProfil);

        log.info("Successfully updated status for PackProfil: {}", id);
    }

    @Transactional
    @Override
    public void deleteProfilsFromPack(String codPackPack, List<String> profilCodes) {
        log.info("deleteProfilsFromPack: pack={}, profiles={}", codPackPack, profilCodes);

        for (String codPflPfl : profilCodes) {
            PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
            if (packProfilRepository.existsById(id)) {
                packProfilRepository.deleteById(id);
                log.info("Deleted PackProfil: {}", id);
            } else {
                log.warn("PackProfil not found for deletion: {}", id);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAssignableProfilesForPack(String managerMatricule, String codPackPack) throws EntityNotFoundException {
        log.info("getAssignableProfilesForPack: manager={}, pack={}", managerMatricule, codPackPack);

        Structure managerStruct = structureRepository.findStructureByUserMatricule(managerMatricule);
        if (managerStruct == null) {
            throw new com.bna.habil.domain.exceptions.EntityNotFoundException("Structure du manager n'existe pas.");
        }

        List<PackProfil> allowed = packProfilRepository
                .findByCodPackPackAndCodTstrcTstrcAndBoolEtat(
                        codPackPack,
                        managerStruct.getCodeTypeStructure().toString(), 1
                );

        log.info("getAssignableProfilesForPack: found {} profiles", allowed.size());

        return allowed.stream()
                .map(PackProfil::getCodPflPfl)
                .distinct()
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isProfileInPack(String codPackPack, String codPflPfl) {
        PackProfilId id = new PackProfilId(codPackPack, codPflPfl);
        return packProfilRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public int countProfilesInPack(String codPackPack) {
        return packProfilRepository.countByCodPackPack(codPackPack);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PackProfilDto deactivatePackProfil(PackProfilId id) {
        log.info("Deactivating PackProfil: Pack={}, Profile={}", id.getCodPackPack(), id.getCodPflPfl());

        PackProfil packProfil = packProfilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_PACK_PROFIL_NOT_FOUND,
                                id.getCodPackPack(), id.getCodPflPfl())));

        packProfil.setBoolEtat(0);
        PackProfil updated = packProfilRepository.save(packProfil);

        log.info("PackProfil deactivated successfully: Pack={}, Profile={}",
                id.getCodPackPack(), id.getCodPflPfl());

        return mapper.toDto(updated);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PackProfilDto activatePackProfil(PackProfilId id) {
        log.info("Activating PackProfil: Pack={}, Profile={}", id.getCodPackPack(), id.getCodPflPfl());

        PackProfil packProfil = packProfilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_PACK_PROFIL_NOT_FOUND,
                                id.getCodPackPack(), id.getCodPflPfl())));

        packProfil.setBoolEtat(1);
        PackProfil updated = packProfilRepository.save(packProfil);

        log.info("PackProfil activated successfully: Pack={}, Profile={}",
                id.getCodPackPack(), id.getCodPflPfl());

        return mapper.toDto(updated);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SyncResult autoSyncPackProfiles(String codPackPack) {
        log.info("🔄 Auto-syncing pack: {}", codPackPack);

        SyncResult result = new SyncResult();
        result.setAddedProfileCodes(new ArrayList<>());
        result.setRemovedProfileCodes(new ArrayList<>());
        result.setUserDetails(new ArrayList<>());

        // 1. Verify pack exists
        if (!packRepository.existsById(codPackPack)) {
            throw new EntityNotFoundException("Pack not found: " + codPackPack);
        }

        // 2. Get all ACTIVE profiles that should be in this pack
        List<PackProfil> expectedProfiles = packProfilRepository
                .findByCodPackPackAndBoolEtat(codPackPack, 1);

        Set<String> expectedProfileCodes = expectedProfiles.stream()
                .map(PackProfil::getCodPflPfl)
                .collect(Collectors.toSet());

        log.info("📦 Pack {} should have {} active profiles: {}",
                codPackPack, expectedProfileCodes.size(), expectedProfileCodes);

        // 3. Get all users who have this pack assigned (ACTIVE)
        List<UtilisateurPack> assignedUsers = utilisateurPackRepository
                .findByCodPackPackAndBoolEtatAffect(codPackPack, 1);

        log.info("👥 Found {} users with pack {} assigned", assignedUsers.size(), codPackPack);

        if (assignedUsers.isEmpty()) {
            log.info("No users to sync");
            return result;
        }

        // ⚠️ 4. DETECT CONFLICTS BEFORE SYNCING (snapshot of current state)
        List<ProfileConflictDto> conflicts = detectProfileConflictsBeforeSync(
                codPackPack, expectedProfileCodes, assignedUsers);
        result.setConflicts(conflicts);

        if (!conflicts.isEmpty()) {
            log.warn("⚠️ Found {} profile conflicts for pack {} BEFORE sync", conflicts.size(), codPackPack);
        }

        // 5. Preload all profiles to avoid N+1 queries
        Map<String, Profil> profilMap = profilRepository.findAllById(expectedProfileCodes)
                .stream()
                .collect(Collectors.toMap(Profil::getCodPflPfl, p -> p));

        // 6. Sync each user
        int totalProfilesAdded = 0;
        int totalProfilesRemoved = 0;
        int usersUpdated = 0;

        for (UtilisateurPack userPack : assignedUsers) {
            String userMatricule = userPack.getNumMatrUser();

            try {
                SyncResult.UserSyncDetail userDetail = syncUserProfiles(
                        userMatricule,
                        codPackPack,
                        expectedProfileCodes,
                        userPack,
                        profilMap
                );

                result.getUserDetails().add(userDetail);

                if (userDetail.isSuccess()) {
                    totalProfilesAdded += userDetail.getProfilesAdded();
                    totalProfilesRemoved += userDetail.getProfilesRemoved();

                    if (userDetail.getProfilesAdded() > 0 || userDetail.getProfilesRemoved() > 0) {
                        usersUpdated++;
                    }
                }

                log.debug("✅ Synced user {}: +{} profiles, -{} profiles",
                        userMatricule, userDetail.getProfilesAdded(), userDetail.getProfilesRemoved());

            } catch (Exception e) {
                log.error("❌ Failed to sync user {}: {}", userMatricule, e.getMessage());

                SyncResult.UserSyncDetail errorDetail = new SyncResult.UserSyncDetail();
                errorDetail.setMatricule(userMatricule);
                errorDetail.setSuccess(false);
                errorDetail.setErrorMessage(e.getMessage());
                result.getUserDetails().add(errorDetail);
            }
        }

        result.setUsersUpdated(usersUpdated);
        result.setProfilesAdded(totalProfilesAdded);
        result.setProfilesRemoved(totalProfilesRemoved);

        log.info("🎉 Auto-sync completed: {} users updated, +{} profiles, -{} profiles",
                usersUpdated, totalProfilesAdded, totalProfilesRemoved);

        return result;
    }

    /**
     * Sync a single user's profiles with the pack's expected profiles
     */
    private SyncResult.UserSyncDetail syncUserProfiles(
            String userMatricule,
            String packCode,
            Set<String> expectedProfileCodes,
            UtilisateurPack userPack,
            Map<String, Profil> profilMap) {

        SyncResult.UserSyncDetail detail = new SyncResult.UserSyncDetail();
        detail.setMatricule(userMatricule);
        detail.setSuccess(true);

        int profilesAdded = 0;
        int profilesRemoved = 0;

        // Step 1: Get all profiles linked to the pack via PackProfil
        List<PackProfil> packProfils = packProfilRepository.findByCodPackPack(packCode);
        Set<String> packProfileCodes = packProfils.stream()
                .map(PackProfil::getCodPflPfl)
                .collect(Collectors.toSet());

        // Step 2: Get user's current profiles (non-custom only)
        List<UtilisateurProfil> currentUserProfiles = utilisateurProfilRepository
                .findByIdNumMatrUserAndBoolCustomProfil(userMatricule, 0);

        // Filter current profiles to only those belonging to this pack
        List<UtilisateurProfil> currentProfilesInPack = currentUserProfiles.stream()
                .filter(up -> packProfileCodes.contains(up.getId().getCodPflPfl()) && up.getBoolEtatUtpr() == 1)
                .toList();

        Set<String> currentProfileCodes = currentProfilesInPack.stream()
                .map(up -> up.getId().getCodPflPfl())
                .collect(Collectors.toSet());

        log.debug("User {} currently has profiles from pack {}: {}", userMatricule, packCode, currentProfileCodes);

        // Find profiles to ADD (in expected but not in current)
        Set<String> profilesToAdd = new HashSet<>(expectedProfileCodes);
        profilesToAdd.removeAll(currentProfileCodes);

        // Find profiles to REMOVE (in current but not in expected)
        Set<String> profilesToRemove = new HashSet<>(currentProfileCodes);
        profilesToRemove.removeAll(expectedProfileCodes);

        log.debug("User {}: Adding {} profiles, Removing {} profiles for pack {}",
                userMatricule, profilesToAdd.size(), profilesToRemove.size(), packCode);

        // ========== ADD MISSING PROFILES ==========
        profilesAdded = addMissingProfiles(userMatricule, userPack, profilMap, profilesToAdd, profilesAdded, packProfileCodes);

        // ========== REMOVE EXTRA PROFILES ==========
        profilesRemoved = removeExtraProfiles(userMatricule, profilesToRemove, packProfileCodes, profilesRemoved);

        detail.setProfilesAdded(profilesAdded);
        detail.setProfilesRemoved(profilesRemoved);

        return detail;
    }

    private int addMissingProfiles(String userMatricule, UtilisateurPack userPack, Map<String, Profil> profilMap, Set<String> profilesToAdd, int profilesAdded, Set<String> packProfileCodes) {
        for (String profilCode : profilesToAdd) {
            UtilisateurProfilId upId = new UtilisateurProfilId(profilCode, userMatricule);
            Optional<UtilisateurProfil> existingOpt = utilisateurProfilRepository.findById(upId);

            if (existingOpt.isEmpty()) {
                // Create new profile assignment
                Profil profil = profilMap.get(profilCode);
                if (profil == null) {
                    log.warn("Profile {} not found in preloaded map", profilCode);
                    continue;
                }

                UtilisateurProfil newUtilisateurProfil = new UtilisateurProfil();
                newUtilisateurProfil.setId(upId);
                newUtilisateurProfil.setProfil(profil);
                // No direct pack or codPackPack set here
                newUtilisateurProfil.setBoolCustomProfil(0); // From pack
                newUtilisateurProfil.setBoolEtatUtpr(1); // Active
                newUtilisateurProfil.setDatdadhutpr(userPack.getDatDebAffect());
                newUtilisateurProfil.setDatFadhUtpr(userPack.getDatFinAffect());

                utilisateurProfilRepository.save(newUtilisateurProfil);
                profilesAdded++;
                log.debug("➕ Added profile {} to user {}", profilCode, userMatricule);

            } else {
                // Reactivate if it was deactivated
                UtilisateurProfil existing = existingOpt.get();

                // Check if profile belongs to this pack via PackProfil association
                if (packProfileCodes.contains(profilCode) &&
                        existing.getBoolCustomProfil() == 0 &&
                        existing.getBoolEtatUtpr() == 0) {

                    existing.setBoolEtatUtpr(1); // Reactivate
                    existing.setDatdadhutpr(userPack.getDatDebAffect());
                    existing.setDatFadhUtpr(userPack.getDatFinAffect());

                    utilisateurProfilRepository.save(existing);
                    profilesAdded++;
                    log.debug("🔄 Reactivated profile {} for user {}", profilCode, userMatricule);
                }
            }
        }
        return profilesAdded;
    }

    private int removeExtraProfiles(String userMatricule, Set<String> profilesToRemove, Set<String> packProfileCodes, int profilesRemoved) {
        for (String profilCode : profilesToRemove) {
            UtilisateurProfilId upId = new UtilisateurProfilId(profilCode, userMatricule);
            Optional<UtilisateurProfil> existingOpt = utilisateurProfilRepository.findById(upId);

            if (existingOpt.isPresent()) {
                UtilisateurProfil existing = existingOpt.get();

                // Only deactivate if profile belongs to this pack and not custom
                if (packProfileCodes.contains(profilCode) &&
                        existing.getBoolCustomProfil() == 0 &&
                        existing.getBoolEtatUtpr() == 1) {

                    existing.setBoolEtatUtpr(0); // Deactivate
                    existing.setDatFadhUtpr(new Date()); // Set end date

                    utilisateurProfilRepository.save(existing);
                    profilesRemoved++;
                    log.debug("➖ Deactivated profile {} for user {}", profilCode, userMatricule);
                }
            }
        }
        return profilesRemoved;
    }

    private List<ProfileConflictDto> detectProfileConflictsBeforeSync(
            String codPackPack,
            Set<String> expectedProfileCodes,
            List<UtilisateurPack> assignedUsers) {

        List<ProfileConflictDto> conflicts = new ArrayList<>();

        List<PackProfil> allPackProfils = packProfilRepository.findByCodPackPack(codPackPack);
        Set<String> allPackProfileCodes = allPackProfils.stream()
                .map(PackProfil::getCodPflPfl)
                .collect(Collectors.toSet());

        for (UtilisateurPack userPack : assignedUsers) {
            String matricule = userPack.getNumMatrUser();

            List<UtilisateurProfil> currentUserProfiles = utilisateurProfilRepository
                    .findByIdNumMatrUser(matricule);

            for (UtilisateurProfil up : currentUserProfiles) {
                String profilCode = up.getId().getCodPflPfl();

                if (!allPackProfileCodes.contains(profilCode)) {
                    continue;
                }

                // Profile is in expected but user has it REVOKED → Sync will reactivate
                if (expectedProfileCodes.contains(profilCode) && up.getBoolEtatUtpr() == 0) {
                    conflicts.add(new ProfileConflictDto(
                            matricule,
                            profilCode,
                            "REVOKED",
                            up.getBoolEtatUtpr()
                    ));
                }

                // Profile is NOT in expected but user has it ACTIVE → Sync will deactivate
                if (!expectedProfileCodes.contains(profilCode) && up.getBoolEtatUtpr() == 1) {
                    conflicts.add(new ProfileConflictDto(
                            matricule,
                            profilCode,
                            "WILL_BE_DEACTIVATED",
                            up.getBoolEtatUtpr()
                    ));
                }
            }
        }

        return conflicts;
    }
}