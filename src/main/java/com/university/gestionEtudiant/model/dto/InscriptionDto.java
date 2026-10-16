package com.university.gestionEtudiant.model.dto;

import java.time.LocalDateTime;

public record InscriptionDto (
        Long id,
        LocalDateTime inscriteLe,
        String inscritePar,
        Long idEtudiant,
        Long idAnnee,
        Long idClasse
)
{
}
