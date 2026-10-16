package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClasseTableDto (
    Long  id,
   // @NotBlank(message = "typeClasse est obligatoire")
    String typeClasse,

    //@NotNull(message = "idFiliere est obligatoire.")
    Long idFiliere,
   // @NotNull(message = "idNiveau est obligatoire.")
    Long idNiveau
)
{}
