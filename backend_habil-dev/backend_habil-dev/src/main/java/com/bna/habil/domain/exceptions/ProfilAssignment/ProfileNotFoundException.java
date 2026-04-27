package com.bna.habil.domain.exceptions.ProfilAssignment;

import java.io.Serial;

public class ProfileNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6256714927728010918L;

    public ProfileNotFoundException(String msg) {
        super(msg);
    }
}
