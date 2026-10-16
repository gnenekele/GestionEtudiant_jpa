package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;

public record parentFormDto (
        @NotBlank(message = "nomParent est obligatoire")
        String nomParent,
        @NotBlank(message = "prenomParent est obligatoire")
        String prenomParent,
        @NotBlank(message = "contactParent est obligatoire")
        String contactParent,
        @NotBlank(message = "email est obligatoire")
        String email,
        @NotBlank(message = "profession est obligatoire")
        String profession

) {

}
