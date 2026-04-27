package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.PackProfilDto;
import com.bna.habil.domain.entities.PackProfil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackProfilMapper extends GenericMapper<PackProfil, PackProfilDto> {
}
