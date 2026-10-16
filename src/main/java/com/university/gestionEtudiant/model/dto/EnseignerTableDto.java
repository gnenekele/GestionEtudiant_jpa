package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;


public record EnseignerTableDto(
        Long id,

        @NotBlank(message = "code est obligatoire")
        String codeEnseigner,
        @NotBlank(message = "Professeur est obligatoire")
        Long idProf,

        @NotBlank(message = "Matiere est obligatoire")
        Long idMatiere

) {


}
