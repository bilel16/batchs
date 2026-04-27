package com.bna.habil.application.mappers;

import java.util.List;

public interface GenericMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDtoList(List<E> entities);

    List<E> toEntityList(List<D> dtos);

    void updateEntityFromDto(D dto, @org.mapstruct.MappingTarget E entity);

}
