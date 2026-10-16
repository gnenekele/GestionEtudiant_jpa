package com.university.gestionEtudiant.model.dto;

public record EmploiDuTempsDto (
        Long id,
        String jour,
        String heureDebut,
        String heureFin,
        String salle,
        Long idMatiere,
        Long idClasse,
        Long idProfesseur

) {


}
