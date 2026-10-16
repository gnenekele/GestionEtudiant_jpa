package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.AbsenceDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AbsenceService {

    AbsenceDto getAbsenceById (Long id);
    List<AbsenceDto> getAllAbsence (Pageable pageable);
    AbsenceDto save (AbsenceDto dto);
    AbsenceDto update (AbsenceDto dto);
    void deleteAbsence (Long id);
}
