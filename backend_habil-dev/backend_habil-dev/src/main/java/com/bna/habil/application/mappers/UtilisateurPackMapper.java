package com.bna.habil.application.mappers;

import com.bna.habil.application.dto.UtilisateurPackDto;
import com.bna.habil.domain.entities.UtilisateurPack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurPackMapper extends GenericMapper<UtilisateurPack, UtilisateurPackDto> {
}
