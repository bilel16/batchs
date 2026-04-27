package com.bna.habil.application.services;

import com.bna.habil.application.dto.AssignedPack;
import com.bna.habil.application.dto.UtilisateurPackDto;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.entities.entitiesId.UtilisateurPackId;
import com.bna.habil.interfaces.response.BatchAssignmentResult;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UtilisateurPackService extends CrudService<UtilisateurPackDto, UtilisateurPackId> {
    @Transactional(readOnly = true)
    List<UtilisateurPackDto> getPacksByMatricule(String matricule) throws EntityNotFoundException, ValidationException;

    @Transactional(rollbackFor = Exception.class)
    BatchAssignmentResult assignMultiplePacksToUser(
            String userMatricule,
            List<AssignedPack> assignedPacks,
            List<String> revokedPacks);
}