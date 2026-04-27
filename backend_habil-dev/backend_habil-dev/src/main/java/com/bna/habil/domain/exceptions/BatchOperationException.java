package com.bna.habil.domain.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Exception for batch operation failures
 */
@Getter
public class BatchOperationException extends BusinessException {
    private final List<BatchError> errors;

    public BatchOperationException(String message, List<BatchError> errors) {
        super(message, "BATCH_OPERATION_FAILED", HttpStatus.MULTI_STATUS);
        this.errors = errors;
    }

    /**
         * Represents a single error in a batch operation
         */
        public record BatchError(int index, String identifier, String error) implements Serializable {
            @Serial
            private static final long serialVersionUID = 2405172841950251807L;

    }
}