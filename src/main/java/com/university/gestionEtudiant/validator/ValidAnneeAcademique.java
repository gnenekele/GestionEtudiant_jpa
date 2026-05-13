package com.university.gestionEtudiant.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnneeAcademiqueValidator.class)
@Documented
public  @interface ValidAnneeAcademique {
    String message() default "L'année de début doit être inférieure à l'année de fin (ex: 2024-2025)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
