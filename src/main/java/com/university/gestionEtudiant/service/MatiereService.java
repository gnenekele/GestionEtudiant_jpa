package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.MatiereDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatiereService {
    MatiereDto save (MatiereDto dto);
    MatiereDto update (MatiereDto dto);
    MatiereDto getMatiereById (Long id);
    List<MatiereDto> getAllMatiere (Pageable pageable);
    void deleteMatiere(Long id);
}
