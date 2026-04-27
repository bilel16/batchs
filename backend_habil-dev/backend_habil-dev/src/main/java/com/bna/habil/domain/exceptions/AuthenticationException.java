package com.bna.habil.domain.exceptions;

import java.io.Serial;

public class AuthenticationException extends Exception {
    @Serial
    private static final long serialVersionUID = 6256714927728010918L;

    public AuthenticationException(String msg) {
        super(msg);
    }
}
