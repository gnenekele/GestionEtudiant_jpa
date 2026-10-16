package com.university.gestionEtudiant.service;
import com.university.gestionEtudiant.model.dto.ProfesseurFormDto;
import com.university.gestionEtudiant.model.dto.ProfesseurTableDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfesseurService  {
    ProfesseurTableDto save (ProfesseurFormDto dto);
    ProfesseurTableDto update (Long id,ProfesseurFormDto dto);
    ProfesseurTableDto getProfById (Long id);
    List<ProfesseurTableDto> getAllProf (Pageable pageable);
    void deleteProf (Long id);
}
