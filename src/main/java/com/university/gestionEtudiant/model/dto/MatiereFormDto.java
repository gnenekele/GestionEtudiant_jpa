package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;

public record MatiereFormDto (
        @NotBlank(message = "libMatiere est obligatoire")
        String libMatiere,
        @NotBlank(message = "coefficient est obligatoire")
        Long coefficient
){
}
