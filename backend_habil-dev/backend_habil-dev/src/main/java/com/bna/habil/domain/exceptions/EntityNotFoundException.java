package com.bna.habil.domain.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an entity is not found
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

}
