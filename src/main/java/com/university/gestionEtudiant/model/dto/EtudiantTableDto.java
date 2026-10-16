package com.university.gestionEtudiant.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EtudiantTableDto (
        Long id,

        // Le matricule est auto-généré, donc pas de @NotNull
        // Il peut être fourni manuellement ou laissé null pour auto-génération
        String matriculeEtudiant,


        @NotBlank(message = "codeEtudiant est obligatoire")
        @Size(max = 10, message = "Le codeEtudiant ne doit pas dépasser 10 caractères")
         String codeEtudiant,

        @Size(max =30, message = "Le nomEtudiant ne doit pas dépasser 30 caractères")
        @NotBlank(message = "nomEtudiant est obligatoire")
        String nomEtudiant,

        @Size(max = 100, message = "Le prenomEtudiant  ne doit pas dépasser 100 caractères")
        @NotBlank(message = "prenomEtudiant est obligatoire")
        String prenomEtudiant,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        @NotBlank(message = "dateNaissEtudiant est obligatoire")
        String dateNaissEtudiant,

        @NotBlank(message = "sexeEtudiant est obligatoire")
        @Size(max = 1, message = "Le sexeEtudiant ne doit pas dépasser 1 caractères")
        String sexeEtudiant,

        @NotBlank(message = "contactEtudiant est obligatoire")
        @Size(max = 30, message = "Le contactEtudiant  ne doit pas dépasser 30 caractères")
         String contactEtudiant,

        @NotBlank(message = "emailEtudiant est obligatoire")
        @Size(max = 100, message = "Le emailEtudiant  ne doit pas dépasser 100 caractères")
         String emailEtudiant,

        @NotNull(message = "idParent est obligatoire")
       Long idParent


    // @NotBlank(message = "nomParent est obligatoire")
    // String contactParent,

   // @NotBlank(message = "nomParent est obligatoire")
     //String nomParent,
    )
{ }
