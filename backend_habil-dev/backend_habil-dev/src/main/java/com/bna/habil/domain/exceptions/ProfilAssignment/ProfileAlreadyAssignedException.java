package com.bna.habil.domain.exceptions.ProfilAssignment;

import java.io.Serial;

public class ProfileAlreadyAssignedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6256714927728010918L;

    public ProfileAlreadyAssignedException(String msg) {
        super(msg);
    }
}
