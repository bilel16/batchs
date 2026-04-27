package com.bna.habil.application.services.impl;


import com.bna.habil.application.dto.AddApplicationDto;
import com.bna.habil.application.dto.ApplicationDto;
import com.bna.habil.application.enums.AdminProfile;
import com.bna.habil.application.services.ApplicationService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.*;
import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.persistence.repositories.ApplicationRepository;
import com.bna.habil.infrastructure.persistence.repositories.PersonnelRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.*;
import com.bna.habil.application.mappers.ApplicationMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ApplicationServiceImpl
        extends AbstractCrudService<Application, ApplicationDto, String>
        implements ApplicationService {

    private static final String ENTITY_NAME = "Application";
    private static final String BNA_HABIL = "BNAHABIL";
    private static final String DEFAULT_USER = "SYSTEM";

    private static final String PACK_CHEF_AGENCE = "PACK_CHEF_AGENCE";
    private static final String PACK_DIR_REGIONAL = "PACK_DIR_REGIONAL";

    private static final Map<String, String> PACK_LABELS = Map.of(
            PACK_CHEF_AGENCE, "Pack Chef d'Agence",
            PACK_DIR_REGIONAL, "Pack Directeur Régional"
    );

    private static final Map<String, String> PACK_LEVELS = Map.of(
            PACK_CHEF_AGENCE, "1",
            PACK_DIR_REGIONAL, "2"
    );
    private static final List<String> DEFAULT_MENU_BNA_HABIL = List.of("ADMIN_USER_PROF", "ADMIN_INTERIM");

    private final ApplicationRepository applicationRepository;
    private final ProfilRepository profilRepository;
    private final PersonnelRepository personnelRepository;
    private final UtilisateurProfilRepository utilisateurProfilRepository;
    private final ApplicationMapper applicationMapper;
    private final ProfilMenuApplicationRepository profilMenuApplicationRepository;
    private final PackRepository packRepository;
    private final PackProfilRepository packProfilRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationMapper mapper,
                                  ProfilRepository profilRepository,
                                  PersonnelRepository personnelRepository,
                                  UtilisateurProfilRepository utilisateurProfilRepository,
                                  ProfilMenuApplicationRepository profilMenuApplicationRepository, PackRepository packRepository, PackProfilRepository packProfilRepository) {
        super(applicationRepository, mapper,
                ApplicationDto::getCodApp,
                id -> "CodApp=" + id,
                ApplicationServiceImpl::validateCreate,
                (id, dto, existing) -> { }
        );
        this.applicationRepository = applicationRepository;
        this.profilRepository = profilRepository;
        this.personnelRepository = personnelRepository;
        this.utilisateurProfilRepository = utilisateurProfilRepository;
        this.applicationMapper = mapper;
        this.profilMenuApplicationRepository = profilMenuApplicationRepository;
        this.packRepository = packRepository;
        this.packProfilRepository = packProfilRepository;
    }

    private static void validateCreate(ApplicationDto dto) {
        requireNonBlank(dto.getCodApp(), "Application code cannot be empty");
        requireNonBlank(dto.getLibApp(), "Application label cannot be empty");
        requireNonBlank(dto.getLibLab(), "Application short label cannot be empty");
    }

    private static void requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(message);
        }
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

    // ─── Combined create/update with profiles ───

    @Transactional
    @Override
    public ApplicationDto createWithProfiles(AddApplicationDto dto) {
        ApplicationDto saved = create(toApplicationDto(dto));
        createProfilesForApplication(dto);
        return saved;
    }

    @Transactional
    @Override
    public ApplicationDto updateWithProfiles(String codApp, AddApplicationDto dto) {
        ApplicationDto appDto = toApplicationDto(dto);
        appDto.setCodApp(codApp);
        ApplicationDto updated = update(codApp, appDto);
        updateProfilesForApplication(codApp, dto);
        return updated;
    }

    private ApplicationDto toApplicationDto(AddApplicationDto dto) {
        ApplicationDto appDto = new ApplicationDto();
        appDto.setCodApp(dto.getCodApp());
        appDto.setLibApp(dto.getLibApp());
        appDto.setLibLab(dto.getLibLab());
        return appDto;
    }

    // ─── Profile management ───

    @Override
    public void createProfilesForApplication(AddApplicationDto dto) {
        for (AdminProfile profile : AdminProfile.values()) {
            if (profile.isEnabled(dto)) {
                createAndSaveProfil(dto.getCodApp(), profile);
            }
        }
    }

    private void createAndSaveProfil(String codApp, AdminProfile profile) {
        Profil profil = new Profil();
        profil.setCodPflPfl(profile.getProfileCode(codApp));
        profil.setLibpflpfl(codApp + " Admin " + profile.getSuffix());
        profil.setCodAppApp(BNA_HABIL);
        profil.setLibhdebpfl("0");
        profil.setLibhfinpfl("24");
        profil.setCodNivhPfl(String.valueOf(profile.getLevel()));
        profil.setBoolEtatPfl("1");
        profil.setBoolJouvPfl("1");
        profil.setCodCatpPfl("0");

        profilRepository.save(profil);
        createDefaultProfilMenuApplication(profil.getCodPflPfl(), profile.getLevel());

        // Assign to pack based on profile type
        if (profile == AdminProfile.AGENCE) {
            assignProfilToPack(profil.getCodPflPfl(), PACK_CHEF_AGENCE);
        } else if (profile == AdminProfile.REGIONAL) {
            assignProfilToPack(profil.getCodPflPfl(), PACK_DIR_REGIONAL);
        }
    }

    private void assignProfilToPack(String codPflPfl, String packCode) {
        Pack pack = packRepository.findById(packCode).orElseGet(() -> {
            Pack newPack = new Pack();
            newPack.setCodPackPack(packCode);
            newPack.setLibPackPack(PACK_LABELS.getOrDefault(packCode, packCode));
            newPack.setCodNivhPfl(PACK_LEVELS.getOrDefault(packCode, "0"));
            newPack.setCodCatpPfl(PACK_LEVELS.getOrDefault(packCode, "0"));
            newPack.setDescPack("Auto-created pack for " + packCode);
            newPack.setBoolActifPack(1);
            newPack.setUserCrePack(DEFAULT_USER);
            return packRepository.save(newPack);
        });

        boolean alreadyLinked = packProfilRepository
                .existsByPackCodPackPackAndCodPflPfl(packCode, codPflPfl);

        if (!alreadyLinked) {
            PackProfil packProfil = new PackProfil();
            packProfil.setPack(pack);
            packProfil.setCodPackPack(packCode);
            packProfil.setCodPflPfl(codPflPfl);
            packProfil.setCodTstrcTstrc(PACK_LEVELS.getOrDefault(packCode, "0"));
            packProfil.setBoolEtat(1);
            packProfilRepository.save(packProfil);
            log.debug("Assigned profil {} to pack {}", codPflPfl, packCode);
        }
    }

    private void createDefaultProfilMenuApplication(String codPflPfl, int level) {
        for (String menuCode : DEFAULT_MENU_BNA_HABIL) {
            ProfilMenuApplication pma = new ProfilMenuApplication();
            pma.setCodAppApp(BNA_HABIL);
            pma.setCodMenuMenu(menuCode);
            pma.setCodPflPfl(codPflPfl);
            pma.setCodTstrcTstrc(String.valueOf(level));
            pma.setBoolEtatPma(1);

            profilMenuApplicationRepository.save(pma);
            log.debug("Created ProfilMenuApplication for profile: {} with menu: {}", codPflPfl, menuCode);
        }
    }

    @Transactional
    @Override
    public void updateProfilesForApplication(String codApp, AddApplicationDto dto) {
        for (AdminProfile profile : AdminProfile.values()) {
            String profilCode = profile.getProfileCode(codApp);
            Optional<Profil> existing = profilRepository.findById(profilCode);

            if (profile.isEnabled(dto)) {
                handleEnableProfile(codApp, profile, existing);
            } else {
                handleDisableProfile(profilCode, profile.getLevel(), existing);
            }
        }
    }

    private void handleEnableProfile(String codApp, AdminProfile profile, Optional<Profil> existing) {
        if (existing.isPresent()) {
            Profil profil = existing.get();
            if ("0".equals(profil.getBoolEtatPfl())) {
                profil.setBoolEtatPfl("1");
                profilRepository.save(profil);
                enableProfilMenuApplications(profile.getProfileCode(codApp), profile.getLevel());
                // Re-assign to pack if needed
                if (profile == AdminProfile.AGENCE) {
                    assignProfilToPack(profil.getCodPflPfl(), PACK_CHEF_AGENCE);
                } else if (profile == AdminProfile.REGIONAL) {
                    assignProfilToPack(profil.getCodPflPfl(), PACK_DIR_REGIONAL);
                }
            }
        } else {
            createAndSaveProfil(codApp, profile);
        }
    }

    private void enableProfilMenuApplications(String codPflPfl, int level) {
        for (String menuCode : DEFAULT_MENU_BNA_HABIL) {
            var id = new ProfilMenuApplicationId(BNA_HABIL, menuCode, codPflPfl, String.valueOf(level));
            Optional<ProfilMenuApplication> existing = profilMenuApplicationRepository.findById(id);
            if (existing.isPresent()) {
                ProfilMenuApplication pma = existing.get();
                pma.setBoolEtatPma(1);
                profilMenuApplicationRepository.save(pma);
                log.debug("Reactivated ProfilMenuApplication for profile: {} with menu: {}", codPflPfl, menuCode);
            } else {
                ProfilMenuApplication pma = new ProfilMenuApplication();
                pma.setCodAppApp(BNA_HABIL);
                pma.setCodMenuMenu(menuCode);
                pma.setCodPflPfl(codPflPfl);
                pma.setCodTstrcTstrc(String.valueOf(level));
                pma.setBoolEtatPma(1);
                profilMenuApplicationRepository.save(pma);
                log.debug("Created ProfilMenuApplication for profile: {} with menu: {}", codPflPfl, menuCode);
            }
        }
    }

    private void handleDisableProfile(String profilCode, int level, Optional<Profil> existing) {
        existing.ifPresent(profil -> {
            profil.setBoolEtatPfl("0");
            profilRepository.save(profil);
            disableProfilMenuApplications(profilCode, level);
        });
    }

    private void disableProfilMenuApplications(String codPflPfl, int level) {
        for (String menuCode : DEFAULT_MENU_BNA_HABIL) {
            var id = new ProfilMenuApplicationId(BNA_HABIL, menuCode, codPflPfl, String.valueOf(level));
            profilMenuApplicationRepository.findById(id).ifPresent(pma -> {
                pma.setBoolEtatPma(0);
                profilMenuApplicationRepository.save(pma);
                log.debug("Deactivated ProfilMenuApplication for profile: {} with menu: {}", codPflPfl, menuCode);
            });
        }
    }

    // ─── Application details ───

    @Override
    public AddApplicationDto getApplicationDetails(String codApp) {
        Application app = applicationRepository.findById(codApp)
                .orElseThrow(() -> new EntityNotFoundException("Application not found: " + codApp));

        ApplicationDto appDto = applicationMapper.toDto(app);

        AddApplicationDto dto = new AddApplicationDto();
        dto.setCodApp(appDto.getCodApp());
        dto.setLibApp(appDto.getLibApp());
        dto.setLibLab(appDto.getLibLab());
        dto.setCentral(isProfileActive(codApp, AdminProfile.CENTRALE));
        dto.setRegional(isProfileActive(codApp, AdminProfile.REGIONAL));
        dto.setAgence(isProfileActive(codApp, AdminProfile.AGENCE));

        return dto;
    }

    private boolean isProfileActive(String codApp, AdminProfile profile) {
        return profilRepository.findById(profile.getProfileCode(codApp))
                .map(p -> "1".equals(p.getBoolEtatPfl()))
                .orElse(false);
    }

    // ─── Authorized applications ───

    @Override
    public List<ApplicationDto> getAllAuthorizedApplications() {
        String currentUser = getCurrentUsername();

        return personnelRepository.findCodeTypeStructureByMat(currentUser)
                .map(codeTypeStructure -> resolveAuthorizedApps(currentUser, codeTypeStructure))
                .orElse(Collections.emptyList());
    }

    private List<ApplicationDto> resolveAuthorizedApps(String currentUser, Integer codeTypeStructure) {
        if (utilisateurProfilRepository.existsSuperAdminHabilAtLevel(currentUser, String.valueOf(codeTypeStructure))) {
            return applicationRepository.findAll().stream()
                    .map(applicationMapper::toDto)
                    .toList();
        }

        String profileSuffix = AdminProfile.getProfileSuffixForStructure(codeTypeStructure);
        if (profileSuffix == null) {
            return Collections.emptyList();
        }

        Set<String> authorizedAppCodes = utilisateurProfilRepository
                .findByIdNumMatrUserAndProfileSuffix(currentUser, profileSuffix)
                .stream()
                .map(up -> extractAppCodeFromProfileCode(up.getProfil().getCodPflPfl()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return authorizedAppCodes.isEmpty()
                ? Collections.emptyList()
                : applicationRepository.findByCodAppAppIn(authorizedAppCodes).stream()
                .map(applicationMapper::toDto)
                .toList();
    }

    private String extractAppCodeFromProfileCode(String profileCode) {
        if (profileCode == null) return null;
        int idx = profileCode.indexOf("_Admin_");
        return idx > 0 ? profileCode.substring(0, idx) : null;
    }

    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            log.warn("Could not get current username, using SYSTEM");
            return DEFAULT_USER;
        }
    }
}