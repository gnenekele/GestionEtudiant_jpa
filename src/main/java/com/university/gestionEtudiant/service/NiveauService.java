package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.NiveauDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NiveauService {

    NiveauDto save (NiveauDto dto);
    NiveauDto update (NiveauDto dto);
    NiveauDto getNiveauById (Long id);
   List<NiveauDto> getAllNiveau (Pageable pageable);
   void deleteNiveau (Long id);
}
