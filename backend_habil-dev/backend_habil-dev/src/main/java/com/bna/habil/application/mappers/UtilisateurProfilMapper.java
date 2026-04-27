package com.bna.habil.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bna.habil.domain.beans.UtilisateurProfilBean;
import com.bna.habil.domain.entities.UtilisateurProfil;

@Mapper(componentModel = "spring")
public interface UtilisateurProfilMapper extends GenericMapper<UtilisateurProfil, UtilisateurProfilBean> {

    @Override
    @Mapping(source = "id.codPflPfl", target = "codPflPfl")
    @Mapping(source = "id.numMatrUser", target = "numMatrUser")
    @Mapping(source = "datFadhUtpr", target = "datFadhUtpr")
    @Mapping(source = "datdadhutpr", target = "datdadhutpr")
    @Mapping(source = "boolEtatUtpr", target = "boolEtatUtpr")
    UtilisateurProfilBean toDto(UtilisateurProfil entity);

    @Override
    List<UtilisateurProfilBean> toDtoList(List<UtilisateurProfil> entities);

    @Override
    @Mapping(source = "codPflPfl", target = "id.codPflPfl")
    @Mapping(source = "numMatrUser", target = "id.numMatrUser")
    UtilisateurProfil toEntity(UtilisateurProfilBean dto);
}