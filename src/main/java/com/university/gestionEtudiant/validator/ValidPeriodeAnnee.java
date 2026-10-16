package com.university.gestionEtudiant.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodeAnneeValidator.class) // Cible la classe ci-dessous
public @interface ValidPeriodeAnnee {
    String message() default "Les dates de la période ne correspondent pas à l'année académique.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
