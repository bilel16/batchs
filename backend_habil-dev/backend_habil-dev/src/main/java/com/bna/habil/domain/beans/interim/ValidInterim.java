package com.bna.habil.domain.beans.interim;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InterimValidator.class)
public @interface ValidInterim {
    String message() default "Données d'intérim invalides";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}