package com.bna.habil.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.domain.entities.Profil;

@Mapper(componentModel = "spring")
public interface ProfilMapper extends GenericMapper<Profil, ProfilDto> {
    ProfilDto toDto(Profil entity);

    List<ProfilDto> toDtoList(List<Profil> entities);


    @Mapping(source = "codPflPfl", target = "codPflPfl")
    @Mapping(source = "libpflpfl", target = "libpflpfl")
    @Mapping(source = "libhdebpfl", target = "libhdebpfl")
    @Mapping(source = "libhfinpfl", target = "libhfinpfl")
    @Mapping(source = "codNivhPfl", target = "codNivhPfl")
    @Mapping(source = "boolEtatPfl", target = "boolEtatPfl")
    @Mapping(source = "boolJouvPfl", target = "boolJouvPfl")
    @Mapping(source = "codAppApp", target = "codAppApp")
    @Mapping(source = "codCatpPfl", target = "codCatpPfl")
    Profil toEntity(ProfilDto dto);
}
