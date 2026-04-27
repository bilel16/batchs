package com.bna.habil.domain.beans.interim;

import com.bna.habil.domain.entities.interim.Interim;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InterimValidator implements ConstraintValidator<ValidInterim, Interim> {

    @Override
    public boolean isValid(Interim interim, ConstraintValidatorContext context) {
        if (interim == null) return true;

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        // 1. dateFin >= dateDebut
        if (interim.getDateDebutInterim() != null && interim.getDateFinInterim() != null) {
            if (interim.getDateFinInterim().before(interim.getDateDebutInterim())) {
                context.buildConstraintViolationWithTemplate(
                        "La date de fin doit être postérieure ou égale à la date de début"
                ).addPropertyNode("dateFinInterim").addConstraintViolation();
                valid = false;
            }
        }

        // 2. Source ≠ Cible
        if (interim.getMatriculeSource() != null && interim.getMatriculeCible() != null) {
            if (interim.getMatriculeSource().equals(interim.getMatriculeCible())) {
                context.buildConstraintViolationWithTemplate(
                        "Un agent ne peut pas être son propre intérimaire"
                ).addPropertyNode("matriculeCible").addConstraintViolation();
                valid = false;
            }
        }

        // 3. Structure origine ≠ destination

        // just in case we needed to make the INterim from code strc to another one with validation


//        if (interim.getCodBctOrigine() != null && interim.getCodBctDestination() != null) {
//            if (interim.getCodBctOrigine().equals(interim.getCodBctDestination())) {
//                context.buildConstraintViolationWithTemplate(
//                        "Le code structure d'origine et de destination ne peuvent pas être identiques"
//                ).addPropertyNode("codBctDestination").addConstraintViolation();
//                valid = false;
//            }
//        }

        return valid;
    }
}
