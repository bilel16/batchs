package com.bna.habil.application.mappers;

import org.mapstruct.Mapper;

import com.bna.habil.application.dto.TypeClientDto;
import com.bna.habil.domain.entities.TypeClient;

@Mapper(componentModel = "spring")
public interface TypeClientMapper extends GenericMapper<TypeClient, TypeClientDto> {
}