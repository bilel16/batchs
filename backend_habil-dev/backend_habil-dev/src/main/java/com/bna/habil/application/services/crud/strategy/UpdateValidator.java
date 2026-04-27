package com.bna.habil.application.services.crud.strategy;

/**
 * Validation strategy for update operations
 */
@FunctionalInterface
public interface UpdateValidator<D, E, I> {
    void validate(I id, D dto, E existing);

    static <D, E, I> UpdateValidator<D, E, I> noValidation() {
        return (id, dto, existing) -> {
        };
    }
}