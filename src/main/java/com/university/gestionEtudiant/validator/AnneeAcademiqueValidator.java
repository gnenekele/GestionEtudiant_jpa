package com.university.gestionEtudiant.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnneeAcademiqueValidator implements ConstraintValidator<ValidAnneeAcademique, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // Laissez le @NotNull gérer le null si besoin

        // 1. Vérification du format via Regex
        if (!value.matches("^\\d{4}-\\d{4}$")) return false;

        // 2. Découpage (ex: "2024-2025" -> ["2024", "2025"])
        String[] parts = value.split("-");
        int debut = Integer.parseInt(parts[0]);
        int fin = Integer.parseInt(parts[1]);

        // 3. La règle métier : début < fin
        // return debut < fin;

        // 3. Règle métier : La différence doit être exactement de 1
        // (Cela vérifie implicitement que debut < fin)
        return (fin - debut) == 1;
    }

}
