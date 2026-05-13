package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.AnneeDto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnneeAcademiqueService {
    AnneeDto save(AnneeDto dto);
    AnneeDto update(AnneeDto dto);
    AnneeDto getAnneeById(Long id);
    List<AnneeDto> getAllAnnee(Pageable pageable);
    void deleteAnnee(Long id);
}
