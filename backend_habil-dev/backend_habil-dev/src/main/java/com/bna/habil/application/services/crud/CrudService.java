package com.bna.habil.application.services.crud;


import java.util.List;

/**
 * Generic CRUD service interface
 *
 * @param <Dto> Data Transfer Object type
 * @param <Id>  ID type (can be simple or composite)
 */
public interface CrudService<Dto, Id> {

    /**
     * Create a single entity
     */
    Dto create(Dto Dto);

    /**
     * Create multiple entities in batch
     */
    List<Dto> createBatch(List<Dto> Dtos);

    /**
     * Update an existing entity
     */
    Dto update(Id id, Dto Dto);

    /**
     * Delete an entity by ID
     */
    void delete(Id id);

    /**
     * Find entity by ID
     */
    Dto findById(Id id);

    /**
     * Find all entities
     */
    List<Dto> findAll();

    /**
     * Check if entity exists
     */
    boolean exists(Id id);
}