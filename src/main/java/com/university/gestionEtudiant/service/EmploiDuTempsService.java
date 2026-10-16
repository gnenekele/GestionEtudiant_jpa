package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.EmploiDuTempsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmploiDuTempsService {
    EmploiDuTempsDto getEmploiTempsById (Long id);
    List<EmploiDuTempsDto> getAllEmploiTemps (Pageable pageable);
    EmploiDuTempsDto save (EmploiDuTempsDto dto);
    EmploiDuTempsDto update (EmploiDuTempsDto dto);
    void deleteEmploiTemps (Long id);

}
