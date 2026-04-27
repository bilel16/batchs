package com.bna.habil.application.services.impl;


import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.bna.habil.application.dto.PersonneSagaDto;
import com.bna.habil.application.mappers.PersonneSagaMapper;
import com.bna.habil.application.services.PersonneSagaService;
import com.bna.habil.domain.entities.PersonneSaga;
import com.bna.habil.infrastructure.persistence.repositories.PersonneSagaCustomRepository;

@Slf4j
@Service
public class PersonneSagaServiceImpl extends AbstractCrudService<PersonneSaga, PersonneSagaDto, Long> implements PersonneSagaService {

    private final PersonneSagaCustomRepository personneSagaCustomRepository;

    public PersonneSagaServiceImpl(PersonneSagaCustomRepository personneSagaCustomRepository,
                                   PersonneSagaMapper mapper) {
        super(
                personneSagaCustomRepository,
                mapper,
                // ID Extractor
                PersonneSagaDto::getId,
                // ID Stringifier (using default toString)
                Object::toString,
                // Create Validator
                dto -> {
                    if (dto.getId() != null) {
                        log.warn("ID should be null for create operation, it will be auto-generated");
                    }
                    // Add other validations as needed
                    validatePersonneSagaData(dto);
                },
                // Update Validator
                (id, dto, existing) -> {
                    log.debug("Validating update for PersonneSaga with ID: {}", id);
                    validatePersonneSagaData(dto);
                    // Add update-specific validations here
                }
        );
        this.personneSagaCustomRepository = personneSagaCustomRepository;
    }

    @Override
    protected String getEntityName() {
        return "PersonneSaga";
    }

    /**
     * Custom validation logic for PersonneSaga
     */
    private static void validatePersonneSagaData(PersonneSagaDto dto) {
        // Add your specific validation rules here
        // Example:
        if (dto.getId() == null) {
            throw new ValidationException("Id person cannot be empty");
        }
    }


    @Override
    public PersonneSaga existPersonneSaga(Long id) throws EntityNotFoundException {

        log.info("Checking if PersonneSaga exists with ID: {}", id);

        if (id == null) {
            throw new ValidationException("ID cannot be null");
        }

        return personneSagaCustomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("PersonneSaga with id %d not found", id)
                ));
    }


}
