package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.EnseignerFormDto;
import com.university.gestionEtudiant.model.dto.EnseignerTableDto;
import com.university.gestionEtudiant.model.dto.ProfesseurTableDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnseignerService {
    EnseignerTableDto save (EnseignerFormDto dto);
    EnseignerTableDto update (Long id,EnseignerFormDto dto);
    EnseignerTableDto getEnseignerById (Long id);
    List<EnseignerTableDto>  getAllEnseigner(Pageable pageable);
    void deleteEnseigner (Long i);
}
