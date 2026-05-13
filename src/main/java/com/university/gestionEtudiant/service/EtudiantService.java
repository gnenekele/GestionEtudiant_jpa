package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.EtudiantDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EtudiantService  {
    EtudiantDto save (EtudiantDto dto);
    EtudiantDto update (EtudiantDto dto);
    EtudiantDto getEtudiantById (Long id);
    List<EtudiantDto> getAllEtudiant (Pageable pageable);
    void deleteEtudiant (Long id);
}
