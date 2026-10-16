package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.ParentTableDto;
import com.university.gestionEtudiant.model.dto.parentFormDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParentService {
    ParentTableDto getParentById (Long id);
    List<ParentTableDto> getAllParent (Pageable pageable);
    ParentTableDto save (parentFormDto dto);
    ParentTableDto update (Long id,parentFormDto dto);
    void deleteParent (Long id);
}
