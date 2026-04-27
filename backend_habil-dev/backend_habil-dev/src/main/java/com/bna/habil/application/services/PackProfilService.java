package com.bna.habil.application.services;

import com.bna.habil.application.dto.PackProfilDto;
import com.bna.habil.application.dto.ProfileConflictDto;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.interfaces.response.SyncResult;
import jakarta.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PackProfilService extends CrudService<PackProfilDto, PackProfilId> {
    @Transactional(readOnly = true)
    List<PackProfilDto> getPackProfilListByPack(String codPackPack) throws ValidationException;

    @Transactional(readOnly = true)
    List<PackProfilDto> getPackProfilListByProfile(String codPflPfl) throws ValidationException;

    @Transactional(readOnly = true)
    List<PackProfilDto> getActivePackProfilsByPack(String codPackPack) throws ValidationException;

    @Transactional(readOnly = true)
    List<PackProfilDto> getPackProfilsByStructureType(String codPackPack, String codTstrcTstrc) throws ValidationException;

    @Transactional
    PackProfilDto addPackProfilList(List<PackProfilDto> listPackProfilDto) throws IllegalStateException;

    @Transactional
    void updatePackProfilStatus(String codPackPack, String codPflPfl, Integer boolEtat);

    @Transactional
    void deleteProfilsFromPack(String codPackPack, List<String> profilCodes);

    @Transactional(readOnly = true)
    List<String> getAssignableProfilesForPack(String managerMatricule, String codPackPack) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    boolean isProfileInPack(String codPackPack, String codPflPfl);

    @Transactional(readOnly = true)
    int countProfilesInPack(String codPackPack);

    PackProfilDto deactivatePackProfil(PackProfilId id);

    PackProfilDto activatePackProfil(PackProfilId id);

    SyncResult autoSyncPackProfiles(String codPackPack);
//
//    @Transactional(readOnly = true)
//    List<ProfileConflictDto> detectProfileConflicts(String codPackPack);
}