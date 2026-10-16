package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.EtudiantFormDto;
import com.university.gestionEtudiant.model.dto.EtudiantTableDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EtudiantService  {
    EtudiantTableDto save (EtudiantFormDto dto);
    EtudiantTableDto update (Long id,EtudiantFormDto dto);
    EtudiantTableDto getEtudiantById (Long id);
    List<EtudiantTableDto> getAllEtudiant (Pageable pageable);
    void deleteEtudiant (Long id);
}
