package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.SegmentStructureDto;
import com.bna.habil.domain.entities.SegmentStructure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SegmentStructureMapper extends GenericMapper<SegmentStructure, SegmentStructureDto> {

    @Mapping(source = "id.codStrcStrc", target = "codStrcStrc")
    @Mapping(source = "id.codIpSegs", target = "codIpSegs")
    @Override
    SegmentStructureDto toDto(SegmentStructure entity);

    @Mapping(source = "codStrcStrc", target = "id.codStrcStrc")
    @Mapping(source = "codIpSegs", target = "id.codIpSegs")
    @Override
    SegmentStructure toEntity(SegmentStructureDto dto);

    @Override
    List<SegmentStructureDto> toDtoList(List<SegmentStructure> entities);

    @Override
    List<SegmentStructure> toEntityList(List<SegmentStructureDto> dtos);
}