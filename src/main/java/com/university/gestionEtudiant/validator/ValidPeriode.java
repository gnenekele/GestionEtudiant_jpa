package com.university.gestionEtudiant.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;  // ← Import manquant

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodeValidator.class)
public @interface ValidPeriode {  // ← @interface (pas interface)

    String message() default "La période doit faire exactement 3 mois";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}