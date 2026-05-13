package com.university.gestionEtudiant.model.dto;

import com.university.gestionEtudiant.validator.ValidPeriode;

import java.time.LocalDate;
@ValidPeriode
public record PeriodeDto (
        Long id,
        String typePeriode,
        LocalDate dateDeb,
        LocalDate dateFin,
        Long idAnnee
){}
