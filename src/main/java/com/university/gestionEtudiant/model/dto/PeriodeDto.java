package com.university.gestionEtudiant.model.dto;

import com.university.gestionEtudiant.validator.ValidPeriode;
import com.university.gestionEtudiant.validator.ValidPeriodeAnnee;
import lombok.Data;

import java.time.LocalDate;

@ValidPeriodeAnnee
@ValidPeriode
public record PeriodeDto (
        Long id,
        String typePeriode,
        LocalDate dateDeb,
        LocalDate dateFin,
        Long idAnnee
){}
