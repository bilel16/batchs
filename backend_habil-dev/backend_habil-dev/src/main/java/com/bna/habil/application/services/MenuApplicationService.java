package com.bna.habil.application.services;


import java.util.List;

import com.bna.habil.application.dto.MenuApplicationDto;
import com.bna.habil.application.dto.statistics.ApplicationStatsDto;
import com.bna.habil.domain.entities.entitiesId.MenuApplicationId;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.exceptions.ValidationException;

public interface MenuApplicationService extends CrudService<MenuApplicationDto, MenuApplicationId> {

    List<MenuApplicationDto> getMenuApplicationListBycodAppApp(String codeApp) throws ValidationException;

    ApplicationStatsDto getApplicationStatistics();
}