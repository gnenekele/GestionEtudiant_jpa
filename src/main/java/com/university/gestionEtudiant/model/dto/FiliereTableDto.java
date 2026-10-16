package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;

public record FiliereTableDto (Long id,
                          String libFiliere,
                          String sigle,
                          String typeFiliere
)

{}
