package com.bna.habil.application.services;

import com.bna.habil.application.dto.PackDto;
import com.bna.habil.application.services.crud.CrudService;
import jakarta.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PackService extends CrudService<PackDto, String> {

    @Transactional(readOnly = true)
    List<PackDto> getActivePacks();

    @Transactional(readOnly = true)
    List<PackDto> getPacksByNiveauHierarchique(String codNivhPfl);

    @Transactional(readOnly = true)
    List<PackDto> getPacksByCategorie(String codCatpPfl);

    @Transactional(readOnly = true)
    List<PackDto> getManagerPacks() throws ValidationException;

    @Transactional(readOnly = true)
    List<PackDto> getAvailablePacksForUser(String targetUserMatricule)
            throws ValidationException;

    @Transactional(readOnly = true)
    List<PackDto> getAvailablePacksForUserNotAssigned(String targetUserMatricule)
            throws ValidationException;
}
