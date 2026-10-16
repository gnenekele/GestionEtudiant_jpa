package com.university.gestionEtudiant.service;
import com.university.gestionEtudiant.model.dto.FiliereFormDto;
import com.university.gestionEtudiant.model.dto.FiliereTableDto;
import org.springframework.data.domain.Pageable;
import java.util.List;



public interface FiliereService {

    FiliereTableDto save (FiliereFormDto dto);
    FiliereTableDto update (Long id,FiliereFormDto dto);
    FiliereTableDto getFiliereById (Long id);
    List<FiliereTableDto> getAllFiliere(Pageable pageable);
    void deleteFiliere (Long id);
}
