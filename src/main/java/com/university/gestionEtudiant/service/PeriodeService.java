package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.PeriodeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeriodeService {
    List<PeriodeDto> getAllPeriode (Pageable pageable);
    PeriodeDto getPeriodeById (Long id);
    PeriodeDto save (PeriodeDto dto);
    PeriodeDto update (PeriodeDto dto);
    void deletePeriode (Long id);

}
