package com.university.gestionEtudiant.model.dto;

import java.util.Date;

public record ProfesseurFormDto (

        String nomProf,
        String prenomProf,
        String sexeProf,
        String contactProf,
        Date dateNaissanceProf,
        String email
){
}
