package com.university.gestionEtudiant.service;
import com.university.gestionEtudiant.model.dto.MatiereFormDto;
import com.university.gestionEtudiant.model.dto.MatiereTableDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatiereService {
    MatiereTableDto save (MatiereFormDto dto);
    MatiereTableDto update (Long id,MatiereFormDto dto);
    MatiereTableDto getMatiereById (Long id);
    List<MatiereTableDto> getAllMatiere (Pageable pageable);
    void deleteMatiere(Long id);
}
