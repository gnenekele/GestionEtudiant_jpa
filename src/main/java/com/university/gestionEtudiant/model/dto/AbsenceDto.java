package com.university.gestionEtudiant.model.dto;

import java.util.Date;

public record AbsenceDto(
        Long id,
        Date dateAbsence,
        boolean statut,
        String motif,
        Long idMatiere,
        Long idEtudiant
) {
}
