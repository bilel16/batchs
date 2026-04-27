package com.bna.habil.application.services;

import com.bna.habil.application.dto.PersonneSagaDto;
import com.bna.habil.domain.entities.PersonneSaga;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.exceptions.EntityNotFoundException;

public interface PersonneSagaService extends CrudService<PersonneSagaDto, Long> {

    PersonneSaga existPersonneSaga(Long id) throws EntityNotFoundException;

}
