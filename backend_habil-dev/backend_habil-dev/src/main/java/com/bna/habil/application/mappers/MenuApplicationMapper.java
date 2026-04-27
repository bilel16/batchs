package com.bna.habil.application.mappers;

import java.util.List;

import org.mapstruct.*;

import com.bna.habil.application.dto.MenuApplicationDto;
import com.bna.habil.domain.entities.MenuApplication;


@Mapper(componentModel = "spring")
public interface MenuApplicationMapper extends GenericMapper<MenuApplication, MenuApplicationDto> {

    @Override
    @Mapping(target = "codAppApp", source = "codAppApp")
    @Mapping(target = "codMenuMenu", source = "codMenuMenu")
    MenuApplication toEntity(MenuApplicationDto dto);

    @Override
    @Mapping(target = "codAppApp", source = "codAppApp")
    @Mapping(target = "codMenuMenu", source = "codMenuMenu")
    MenuApplicationDto toDto(MenuApplication entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "codAppApp", source = "codAppApp")
    @Mapping(target = "codMenuMenu", source = "codMenuMenu")
    void updateEntityFromDto(MenuApplicationDto dto, @MappingTarget MenuApplication entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<MenuApplicationDto> toDtoList(List<MenuApplication> entities);

}
