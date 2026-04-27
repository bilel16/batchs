package com.bna.habil.application.mappers;

import java.util.List;

import com.bna.habil.application.dto.StructureWithSegmentsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bna.habil.application.dto.StructureDto;
import com.bna.habil.domain.entities.Structure;


@Mapper(componentModel = "spring")
public interface StructureMapper extends GenericMapper<Structure, StructureDto> {

    @Override
    StructureDto toDto(Structure entity);

    @Override
    Structure toEntity(StructureDto dto);

    @Override
    List<StructureDto> toDtoList(List<Structure> entities);

    @Override
    List<Structure> toEntityList(List<StructureDto> dtos);

    @Override
    void updateEntityFromDto(StructureDto dto, @MappingTarget Structure entity);

    @Mapping(target = "segments", ignore = true)
        // We'll set this manually
    StructureWithSegmentsDto toStructureWithSegmentsDto(Structure structure);
}