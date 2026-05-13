package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.ProfesseurDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfesseurService  {
    ProfesseurDto save (ProfesseurDto dto);
    ProfesseurDto update (ProfesseurDto dto);
    ProfesseurDto getProfById (Long id);
    List<ProfesseurDto> getAllProf (Pageable pageable);
    void deleteProf (Long id);
}
