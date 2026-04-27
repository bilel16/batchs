package com.bna.habil.application.services.impl;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bna.habil.application.dto.RoleUpdateDTO;
import com.bna.habil.application.dto.UserRoleDTO;
import com.bna.habil.application.mappers.ProfilMenuApplicationMapper;
import com.bna.habil.application.services.ProfilMenuApplicationService;
import com.bna.habil.domain.beans.ProfilMenuApplicationBean;
import com.bna.habil.domain.entities.ProfilMenuApplication;
import com.bna.habil.domain.entities.Structure;
import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;
import com.bna.habil.domain.repositories.UserRoleProjection;
import com.bna.habil.infrastructure.persistence.repositories.StructureCustomRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.ProfilMenuApplicationRepository;


@Service
public class ProfilMenuApplicationServiceImpl
        extends AbstractCrudService<ProfilMenuApplication, ProfilMenuApplicationBean, ProfilMenuApplicationId>
        implements ProfilMenuApplicationService {

    private final ProfilMenuApplicationRepository profilMenuApplicationRepository;
    private final StructureCustomRepository structureRepository;

    public ProfilMenuApplicationServiceImpl(ProfilMenuApplicationRepository profilMenuApplicationRepository,
                                            ProfilMenuApplicationMapper mapper, StructureCustomRepository structureRepository) {
        super(
                profilMenuApplicationRepository,
                mapper,
                // ID Extractor
                dto -> new ProfilMenuApplicationId(
                        dto.getCodAppApp(),
                        dto.getCodMenuMenu(),
                        dto.getCodPflPfl(),
                        dto.getCodTstrcTstrc()
                ),
                // ID Stringifier
                id -> String.format("App=%s,Menu=%s,Profil=%s,Tstrc=%s",
                        id.getCodAppApp(), id.getCodMenuMenu(),
                        id.getCodPflPfl(), id.getCodTstrcTstrc()),
                // Create Validator
                dto -> {
                    if (dto.getCodAppApp() == null || dto.getCodAppApp().trim().isEmpty()) {
                        throw new ValidationException("Application code cannot be empty");
                    }
                    if (dto.getCodMenuMenu() == null || dto.getCodMenuMenu().trim().isEmpty()) {
                        throw new ValidationException("Menu code cannot be empty");
                    }
                    if (dto.getCodPflPfl() == null || dto.getCodPflPfl().trim().isEmpty()) {
                        throw new ValidationException("Profile code cannot be empty");
                    }
                    if (dto.getCodTstrcTstrc() == null || dto.getCodTstrcTstrc().trim().isEmpty()) {
                        throw new ValidationException("Structure code cannot be empty");
                    }
                },
                // Update Validator
                (id, dto, existing) -> {
                    if (dto.getBoolEtatPma() != null && dto.getBoolEtatPma() < 0) {
                        throw new ValidationException("Status must be non-negative");
                    }
                }
        );
        this.profilMenuApplicationRepository = profilMenuApplicationRepository;
        this.structureRepository = structureRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProfilMenuApplicationServiceImpl.class);

    @Override
    protected String getEntityName() {
        return "ProfilMenuApplication";
    }

    @Override
    protected Set<ProfilMenuApplicationId> findExistingIds(List<ProfilMenuApplicationId> ids) {
        if (ids.isEmpty()) {
            return Collections.emptySet();
        }
        // For composite keys, check individually
        // Could be optimized with a custom query for large batches
        return ids.stream()
                .filter(repository::existsById)
                .collect(Collectors.toSet());
    }

    @Override
    public List<ProfilMenuApplicationBean> getProfApplicationListBycodAppApp(String codAppApp) {
        List<ProfilMenuApplication> profilMenuApplicationList = profilMenuApplicationRepository.findByCodAppApp(codAppApp);
        return mapper.toDtoList(profilMenuApplicationList);
    }


    @Override
    @Transactional
    public void saveUserRoles(String numMatrUser, String codAppApp, List<RoleUpdateDTO> updates) {
        // Optional: validate that the user actually has the profil(s) — you can check utilisateur_profil
        for (RoleUpdateDTO r : updates) {
            // state must be 0 or 1
            Integer state = (r.getBoolEtatPma() != null && r.getBoolEtatPma() == 1) ? 1 : 0;
            profilMenuApplicationRepository.updateBoolEtatForRole(codAppApp, r.getCodMenuMenu(), r.getCodPflPfl(), state);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleDTO> getUserRolesForApplication(String numMatrUser, String codAppApp) {
        List<UserRoleProjection> list = profilMenuApplicationRepository.findUserRolesInApplication(numMatrUser, codAppApp);
        return list.stream().map(p -> {
            UserRoleDTO dto = new UserRoleDTO();
            dto.setCodPflPfl(p.getCodPflPfl());
            dto.setCodMenuMenu(p.getCodMenuMenu());
            dto.setLibMenuMenu(p.getLibMenuMenu());
            dto.setBoolEtatPma(p.getBoolEtatPma());
            return dto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<String> getAssignableProfiles(String managerMatricule, String appCode) throws EntityNotFoundException {
        Structure managerStruct = structureRepository.findStructureByUserMatricule(managerMatricule);
        if (managerStruct == null) {
            throw new EntityNotFoundException("Structure du manager introuvable.");
        }

        List<ProfilMenuApplication> allowed =
                profilMenuApplicationRepository.findActiveProfilesByAppAndStructureType(appCode, managerStruct.getCodeTypeStructure().toString());
        logger.info(" allowedallowedallowed: {}", allowed);
        return allowed.stream()
                .map(ProfilMenuApplication::getCodPflPfl)
                .distinct()
                .toList();
    }

}