package com.university.gestionEtudiant.service;


import com.university.gestionEtudiant.model.dto.FiliereDto;

import org.springframework.data.domain.Pageable;
import java.util.List;



public interface FiliereService {

    FiliereDto save (FiliereDto dto);
    FiliereDto update (FiliereDto dto);
    FiliereDto getFiliereById (Long id);
    List<FiliereDto> getAllFiliere(Pageable pageable);
    void deleteFiliere (Long id);
}
