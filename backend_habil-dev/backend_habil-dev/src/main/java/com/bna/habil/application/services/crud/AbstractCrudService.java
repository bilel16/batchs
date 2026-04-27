package com.bna.habil.application.services.crud;

import java.util.*;
import java.util.stream.Collectors;

import com.bna.habil.application.services.crud.model.BatchOperationResult;
import com.bna.habil.application.services.crud.strategy.CreateValidator;
import com.bna.habil.application.services.crud.strategy.IdExtractor;
import com.bna.habil.application.services.crud.strategy.IdStringifier;
import com.bna.habil.application.services.crud.strategy.UpdateValidator;
import com.bna.habil.domain.exceptions.BatchOperationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bna.habil.application.mappers.GenericMapper;
import com.bna.habil.domain.exceptions.DuplicateResourceException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract CRUD service following SOLID principles
 *
 * @param <E> JPA E type
 * @param <D>    Data Transfer Object type
 * @param <I>     I type (can be simple or composite)
 */
@Slf4j
public abstract class AbstractCrudService<E, D, I>
        implements CrudService<D, I> {

    protected final JpaRepository<E, I> repository;
    protected final GenericMapper<E, D> mapper;
    protected final IdExtractor<D, I> idExtractor;
    protected final IdStringifier<I> idStringifier;
    protected final CreateValidator<D> createValidator;
    protected final UpdateValidator<D, E, I> updateValidator;

    private static final String ID_NULL_ERROR = "ID cannot be null";
    private static final String ENTITY_NOT_FOUND_MESSAGE = "%s with I %s not found";

    /**
     * Full constructor with all strategies
     */
    protected AbstractCrudService(JpaRepository<E, I> repository, GenericMapper<E, D> mapper, IdExtractor<D, I> idExtractor, IdStringifier<I> idStringifier, CreateValidator<D> createValidator, UpdateValidator<D, E, I> updateValidator) {
        this.repository = repository;
        this.mapper = mapper;
        this.idExtractor = idExtractor;
        this.idStringifier = idStringifier;
        this.createValidator = createValidator;
        this.updateValidator = updateValidator;
    }

    /**
     * Simplified constructor with default validators
     */
    protected AbstractCrudService(
            JpaRepository<E, I> repository,
            GenericMapper<E, D> mapper,
            IdExtractor<D, I> idExtractor) {

        this(repository, mapper, idExtractor,
                IdStringifier.defaultStringifier(),
                CreateValidator.noValidation(),
                UpdateValidator.noValidation());
    }

    // ==================== CREATE OPERATIONS ====================

    @Transactional(rollbackFor = Exception.class)
    @Override
    public D create(D dto) {
        if (dto == null) {
            throw new ValidationException("D cannot be null");
        }

        I id = idExtractor.extractId(dto);
        String idStr = idStringifier.stringify(id);

        log.debug("Creating {} with ID: {}", getEntityName(), idStr);

        // Check for duplicates
        validateNotExists(id, idStr);

        // Custom domain validations
        createValidator.validate(dto);

        // Persist
        E e = mapper.toEntity(dto);
        E saved = repository.save(e);

        log.info("Successfully created {} with ID: {}", getEntityName(), idStr);
        return mapper.toDto(saved);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<D> createBatch(List<D> dtos) {
        BatchOperationResult<D> result = createBatch(dtos, BatchOperationResult.BatchMode.ALL_OR_NOTHING);
        return result.successful();
    }

    /**
     * Batch create with configurable error handling
     */
    @Transactional(rollbackFor = Exception.class)
    public BatchOperationResult<D> createBatch(List<D> dtos, BatchOperationResult.BatchMode mode) {
        validateBatchInput(dtos);
        log.debug("Batch creating {} {} in {} mode", dtos.size(), getEntityName(), mode);

        List<D> successful = new ArrayList<>();
        List<BatchOperationException.BatchError> failed = new ArrayList<>();

        // Pre-validation: Check for duplicates in the batch itself
        checkForDuplicatesInBatch(dtos, failed, mode);

        // Check for existing records and process
        Set<I> existingIds = findExistingIdsFromDtos(dtos);
        processBatchItems(dtos, existingIds, successful, failed, mode);

        handleFinalValidation(failed, mode);
        repository.flush();

        return new BatchOperationResult<>(successful, failed, mode);
    }

    // ==================== UPDATE OPERATIONS ====================

    @Transactional(rollbackFor = Exception.class)
    @Override
    public D update(I id, D dtos) {
        if (id == null) {
            throw new ValidationException(ID_NULL_ERROR);
        }
        if (dtos == null) {
            throw new ValidationException("D cannot be null");
        }

        String idStr = idStringifier.stringify(id);
        log.debug("Updating {} with ID: {}", getEntityName(), idStr);

        E existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ENTITY_NOT_FOUND_MESSAGE, getEntityName(), idStr)
                ));

        // Custom validations
        updateValidator.validate(id, dtos, existing);

        // Update
        E e = mapper.toEntity(dtos);
        E updated = repository.save(e);

        log.info("Successfully updated {} with ID: {}", getEntityName(), idStr);
        return mapper.toDto(updated);
    }

    // ==================== DELETE OPERATIONS ====================

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(I id) {
        if (id == null) {
            throw new ValidationException(ID_NULL_ERROR);
        }

        String idStr = idStringifier.stringify(id);
        log.debug("Deleting {} with ID: {}", getEntityName(), idStr);

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format(ENTITY_NOT_FOUND_MESSAGE, getEntityName(), idStr)
            );
        }

        repository.deleteById(id);
        log.info("Successfully deleted {} with ID: {}", getEntityName(), idStr);
    }

    // ==================== READ OPERATIONS ====================

    @Override
    @Transactional(readOnly = true)
    public D findById(I id) {
        if (id == null) {
            throw new ValidationException(ID_NULL_ERROR);
        }

        String idStr = idStringifier.stringify(id);
        log.debug("Finding {} with ID: {}", getEntityName(), idStr);

        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ENTITY_NOT_FOUND_MESSAGE, getEntityName(), idStr)
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        log.debug("Finding all {}", getEntityName());
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(I id) {
        if (id == null) {
            return false;
        }
        return repository.existsById(id);
    }


    // ==================== PROTECTED HELPER METHODS ====================

    protected void validateNotExists(I id, String idStr) {
        if (repository.existsById(id)) {
            throw new DuplicateResourceException(
                    String.format("%s with I %s already exists", getEntityName(), idStr)
            );
        }
    }

    protected Set<I> findExistingIds(List<I> ids) {
        if (ids.isEmpty()) {
            return Collections.emptySet();
        }
        return ids.stream()
                .filter(repository::existsById)
                .collect(Collectors.toSet());
    }

    protected abstract String getEntityName();


    private void validateBatchInput(List<D> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new ValidationException("D list cannot be null or empty");
        }
    }

    private void checkForDuplicatesInBatch(List<D> dtos,
                                           List<BatchOperationException.BatchError> failed,
                                           BatchOperationResult.BatchMode mode) {
        Set<I> idsInBatch = new HashSet<>();
        for (int i = 0; i < dtos.size(); i++) {
            I id = idExtractor.extractId(dtos.get(i));
            if (!idsInBatch.add(id)) {
                handleDuplicateInBatch(i, id, failed, mode);
            }
        }
    }

    private void handleDuplicateInBatch(int index, I id,
                                        List<BatchOperationException.BatchError> failed,
                                        BatchOperationResult.BatchMode mode) {
        String idStr = idStringifier.stringify(id);
        String error = String.format("Duplicate ID in batch: %s", idStr);
        failed.add(new BatchOperationException.BatchError(index, idStr, error));

        if (mode == BatchOperationResult.BatchMode.ALL_OR_NOTHING) {
            throw new BatchOperationException("Duplicate IDs found in batch", failed);
        }
    }

    private Set<I> findExistingIdsFromDtos(List<D> dtos) {
        List<I> idsToCheck = dtos.stream()
                .map(idExtractor::extractId)
                .toList();
        return findExistingIds(idsToCheck);
    }

    private void processBatchItems(List<D> dtos,
                                   Set<I> existingIds,
                                   List<D> successful,
                                   List<BatchOperationException.BatchError> failed,
                                   BatchOperationResult.BatchMode mode) {
        for (int i = 0; i < dtos.size(); i++) {
            processSingleItem(dtos.get(i), i, existingIds, successful, failed, mode);
        }
    }

    private void processSingleItem(D dtos, int index, Set<I> existingIds,
                                   List<D> successful,
                                   List<BatchOperationException.BatchError> failed,
                                   BatchOperationResult.BatchMode mode) {
        I id = idExtractor.extractId(dtos);
        String idStr = idStringifier.stringify(id);

        try {
            validateNotExists(id, idStr, existingIds);
            createValidator.validate(dtos);
            successful.add(saveAndConvert(dtos));
        } catch (Exception e) {
            handleItemError(index, idStr, e, failed, mode);
        }
    }

    private void validateNotExists(I id, String idStr, Set<I> existingIds) {
        if (existingIds.contains(id)) {
            throw new DuplicateResourceException(
                    String.format("%s with I %s already exists", getEntityName(), idStr)
            );
        }
    }

    private D saveAndConvert(D dto) {
        E e = mapper.toEntity(dto);
        E saved = repository.save(e);
        return mapper.toDto(saved);
    }

    private void handleItemError(int index, String idStr, Exception e,
                                 List<BatchOperationException.BatchError> failed,
                                 BatchOperationResult.BatchMode mode) {
        log.error("Failed to create {} at index {} with ID {}: {}",
                getEntityName(), index, idStr, e.getMessage());
        failed.add(new BatchOperationException.BatchError(index, idStr, e.getMessage()));

        if (mode == BatchOperationResult.BatchMode.ALL_OR_NOTHING) {
            throw new BatchOperationException(
                    String.format("Batch operation failed at index %d", index), failed);
        }
    }

    private void handleFinalValidation(List<BatchOperationException.BatchError> failed,
                                       BatchOperationResult.BatchMode mode) {
        if (mode == BatchOperationResult.BatchMode.ALL_OR_NOTHING && !failed.isEmpty()) {
            throw new BatchOperationException("Batch operation completed with errors", failed);
        }
    }
}
