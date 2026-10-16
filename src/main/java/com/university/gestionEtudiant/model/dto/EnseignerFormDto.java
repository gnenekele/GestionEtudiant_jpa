package com.university.gestionEtudiant.model.dto;

import com.university.gestionEtudiant.model.entity.Matiere;

public record EnseignerFormDto(
                               String codeEnseigner,
                               Long idProf,
                               Long idMatiere
                               ) {
}
