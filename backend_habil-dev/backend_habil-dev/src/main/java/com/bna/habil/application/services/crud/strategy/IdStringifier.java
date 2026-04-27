package com.bna.habil.application.services.crud.strategy;

/**
 * Strategy for creating Id string representation
 * Useful for logging and error messages
 */
@FunctionalInterface
public interface IdStringifier<I> {
    String stringify(I id);

    static <I> IdStringifier<I> defaultStringifier() {
        return id -> id == null ? "null" : id.toString();
    }
}