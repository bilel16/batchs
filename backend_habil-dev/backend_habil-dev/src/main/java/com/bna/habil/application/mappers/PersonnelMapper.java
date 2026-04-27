package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.PersonnelDto;
import com.bna.habil.domain.entities.Personnel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonnelMapper extends GenericMapper<Personnel, PersonnelDto> {
    @Mapping(source = "matricule", target = "mat")
    @Mapping(source = "active", target = "cod_stat_user")
    @Mapping(source = "structureId", target = "cod_strc_strc")
    @Mapping(source = "cin", target = "cin")
    Personnel toEntity(PersonnelDto dto);
}
