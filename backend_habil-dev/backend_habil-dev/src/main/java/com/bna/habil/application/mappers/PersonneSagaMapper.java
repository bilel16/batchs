package com.bna.habil.application.mappers;

import org.mapstruct.Mapper;

import com.bna.habil.application.dto.PersonneSagaDto;
import com.bna.habil.domain.entities.PersonneSaga;

@Mapper(componentModel = "spring")
public interface PersonneSagaMapper extends GenericMapper<PersonneSaga, PersonneSagaDto> {

    @Override
    PersonneSaga toEntity(PersonneSagaDto dto);

    @Override
    PersonneSagaDto toDto(PersonneSaga entity);
}
