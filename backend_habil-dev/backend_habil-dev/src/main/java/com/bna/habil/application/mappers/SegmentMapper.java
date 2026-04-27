package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.SegmentDto;
import com.bna.habil.domain.entities.Segment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SegmentMapper extends GenericMapper<Segment, SegmentDto> {

    @Override
    SegmentDto toDto(Segment entity);

    @Override
    Segment toEntity(SegmentDto dto);

    @Override
    List<SegmentDto> toDtoList(List<Segment> entities);

    @Override
    List<Segment> toEntityList(List<SegmentDto> dtos);

    @Override
    void updateEntityFromDto(SegmentDto dto, @MappingTarget Segment entity);
}