package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.InterimDetailsDto;
import com.bna.habil.application.dto.InterimDto;
import com.bna.habil.domain.entities.interim.Interim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InterimMapper extends GenericMapper<Interim, InterimDto> {
    //fields that should not be modified by the end user
    //Once created, some fields should never change.
    //Because they affect habilitation propagation.
    //you want to change the user mat ?? -> cancel the interim and create another one
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etat", ignore = true)
    @Mapping(target = "dateOperation", ignore = true)
    @Mapping(target = "matriculeSource", ignore = true)
    @Mapping(target = "matriculeCible", ignore = true)
    @Mapping(target = "codStrcOrigine", ignore = true)

    void updateEntityFromDto(InterimDto dto, @MappingTarget Interim entity);

    InterimDetailsDto toDetailsDto(Interim interim);
}