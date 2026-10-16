package com.university.gestionEtudiant.model.dto;

public record NoteDto (
        Long id,
        String libNote,
         Long idEvaluation,
        Long idEtudiant

) {
}
