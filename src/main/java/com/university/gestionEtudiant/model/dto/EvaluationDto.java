package com.university.gestionEtudiant.model.dto;

import java.time.LocalDateTime;

public record EvaluationDto (
        Long id,
        String libEvaluation,
        LocalDateTime dateEvaluation,
        Long idPeriode,
        Long idMatiere
){
}
