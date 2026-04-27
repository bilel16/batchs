package com.bna.habil.application.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.bna.habil.domain.beans.ProfilMenuApplicationBean;
import com.bna.habil.domain.entities.ProfilMenuApplication;


@Mapper(componentModel = "spring", uses = {TypeClientMapper.class})
public interface ProfilMenuApplicationMapper
        extends GenericMapper<ProfilMenuApplication, ProfilMenuApplicationBean> {

    @Override
    ProfilMenuApplicationBean toDto(ProfilMenuApplication entity);

    @Override
    List<ProfilMenuApplicationBean> toDtoList(List<ProfilMenuApplication> entities);

    @Override
    ProfilMenuApplication toEntity(ProfilMenuApplicationBean dto);
}
