package com.bna.habil.application.services.crud.strategy;


/**
 * Strategy for extracting Id from Dto
 * Handles both simple and composite keys
 */
@FunctionalInterface
public interface IdExtractor<D, I> {
    I extractId(D dto);
}