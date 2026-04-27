package com.bna.habil.application.services.crud.strategy;

/**
 * Validation strategy for create operations
 */
@FunctionalInterface
public interface CreateValidator<D> {
    void validate(D dto);

    static <D> CreateValidator<D> noValidation() {
        return dto -> {
        };
    }
}