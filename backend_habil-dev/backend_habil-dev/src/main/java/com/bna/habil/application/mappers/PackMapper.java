package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.PackDto;
import com.bna.habil.domain.entities.Pack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackMapper extends GenericMapper<Pack, PackDto> {
}
