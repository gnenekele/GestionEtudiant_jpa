package com.university.gestionEtudiant.model.dto;

public record EtudiantFormDto(
        //String matriculeEtudiant,
        //String codeEtudiant,
        String nomEtudiant,
        String prenomEtudiant,
        String dateNaissEtudiant,
        String sexeEtudiant,
        String contactEtudiant,
        String emailEtudiant,
        Long idParent
        ) {
}
