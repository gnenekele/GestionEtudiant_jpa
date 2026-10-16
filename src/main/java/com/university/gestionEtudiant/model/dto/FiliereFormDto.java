package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;

public record FiliereFormDto (
        @NotBlank(message = "libFiliere est obligatoire")
        String libFiliere,
        String sigle,
        @NotBlank(message = "typeFiliere est obligatoire")
        String typeFiliere
) {
}
