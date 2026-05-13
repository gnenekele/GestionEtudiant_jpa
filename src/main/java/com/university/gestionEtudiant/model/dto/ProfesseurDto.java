package com.university.gestionEtudiant.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ProfesseurDto (
        Long id,

        @NotBlank(message = "codeProf est obligatoire")
        String codeProf,

        @NotBlank(message = "nomProf est obligatoire")
        String nomProf,

        @NotBlank(message = "prenomProf est obligatoire")
        String prenomProf,

        @NotBlank(message = "matriculeProf est obligatoire")
         String matriculeProf,

        @NotBlank(message = "contactProf est obligatoire")
         String contactProf,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        @NotNull(message = "dateNaissanceProf est obligatoire")
        Date dateNaissanceProf,

        @NotBlank(message = "email est obligatoire")
         String email,

        @NotBlank(message = "sexeProf est obligatoire")
         String sexeProf
)
{}
