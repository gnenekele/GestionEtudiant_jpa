package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClasseDto {
    private long  id;
    @NotNull(message = "idClasse est obligatoire et id ")
    private long idClasse;
    private long idFiliere;
    private long idNiveau;
    @NotBlank(message = "typeClasse est obligatoire")
    private String typeClasse;
}
