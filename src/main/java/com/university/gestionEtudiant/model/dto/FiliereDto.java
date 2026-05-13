package com.university.gestionEtudiant.model.dto;

import jakarta.validation.constraints.NotBlank;

public record FiliereDto (Long id,
                          @NotBlank(message = "libFiliere est obligatoire")
                          String libFiliere,
                          String sigle)

{}
