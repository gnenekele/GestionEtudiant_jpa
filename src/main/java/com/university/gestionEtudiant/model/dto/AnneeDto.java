package com.university.gestionEtudiant.model.dto;

import com.university.gestionEtudiant.validator.ValidAnneeAcademique;
import jakarta.validation.constraints.NotBlank;


public record AnneeDto (
        Long id,
        @NotBlank(message = "L'année est obligatoire")

        @ValidAnneeAcademique
        String annee) {
}
