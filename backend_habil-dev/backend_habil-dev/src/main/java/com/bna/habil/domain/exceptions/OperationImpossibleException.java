package com.bna.habil.domain.exceptions;


import java.io.Serial;

public class OperationImpossibleException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6256714927728010918L;

    public OperationImpossibleException(String msg) {
        super(msg);
    }
}
