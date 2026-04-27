package com.bna.habil.application.mappers;


import com.bna.habil.application.dto.UtilisateurProfilDTO;
import com.bna.habil.domain.entities.UtilisateurProfil;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfilMapper.class})
public interface UtilisateurProfilMapperDetailed extends GenericMapper<UtilisateurProfil, UtilisateurProfilDTO> {

    @Override
    @Mapping(source = "id.numMatrUser", target = "numMatrUser")
    @Mapping(source = "id.codPflPfl", target = "codPflPfl")
        // MapStruct will automatically use ProfilMapper for the profil field
    UtilisateurProfilDTO toDto(UtilisateurProfil entity);

    @Override
    @Mapping(source = "numMatrUser", target = "id.numMatrUser")
    @Mapping(source = "codPflPfl", target = "id.codPflPfl")
    UtilisateurProfil toEntity(UtilisateurProfilDTO dto);

    @Override
    List<UtilisateurProfilDTO> toDtoList(List<UtilisateurProfil> entities);

    @Override
    List<UtilisateurProfil> toEntityList(List<UtilisateurProfilDTO> dtos);

    @Override
    @Mapping(source = "numMatrUser", target = "id.numMatrUser")
    @Mapping(source = "codPflPfl", target = "id.codPflPfl")
    void updateEntityFromDto(UtilisateurProfilDTO dto, @MappingTarget UtilisateurProfil entity);
}