package com.university.gestionEtudiant.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

public record EtudiantDto (
        Long id,
        @NotBlank(message = "codeEtudiant est obligatoire")
     String codeEtudiant,

        @NotBlank(message = "nomEtudiant est obligatoire")
     String nomEtudiant,

        @NotBlank(message = "prenomEtudiant; est obligatoire")
     String prenomEtudiant,

        @NotBlank(message = "matriculeEtudiant est obligatoire")
     String matriculeEtudiant,

    @NotBlank(message = "contactEtudiant est obligatoire")
     String contactEtudiant,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @NotBlank(message = "dateNaissEtudiant est obligatoire")
     String dateNaissEtudiant,

    @NotBlank(message = "emailEtudiant est obligatoire")
     String emailEtudiant,

    @NotBlank(message = "nomParent est obligatoire")
     String contactParent,

    @NotBlank(message = "nomParent est obligatoire")
     String nomParent,
    @NotBlank(message = "sexeEtudiant est obligatoire")
     String sexeEtudiant )
{ }
