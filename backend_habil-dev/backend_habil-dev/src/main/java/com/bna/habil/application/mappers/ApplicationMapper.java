package com.bna.habil.application.mappers;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bna.habil.application.dto.ApplicationDto;
import com.bna.habil.domain.entities.Application;


@Mapper(componentModel = "spring")
public interface ApplicationMapper extends GenericMapper<Application, ApplicationDto> {

    // DTO → Entity (for create, full mapping)
    @Mapping(target = "cod_app_app", source = "codApp")
    @Mapping(target = "lib_app_app", source = "libApp")
    @Mapping(target = "lib_lab_app", source = "libLab")
    Application toEntity(ApplicationDto dto);

    // Entity → DTO
    @Mapping(target = "codApp", source = "cod_app_app")
    @Mapping(target = "libApp", source = "lib_app_app")
    @Mapping(target = "libLab", source = "lib_lab_app")
    ApplicationDto toDto(Application app);

    // Partial update: update only non-null fields from DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cod_app_app", ignore = true)
    // don’t overwrite id
    void updateEntityFromDto(ApplicationDto dto, @MappingTarget Application app);
}

