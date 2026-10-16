package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.EvaluationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EvaluationService {
    List<EvaluationDto> getAllEvaluation (Pageable pageable);
    EvaluationDto getEvaluationById (Long id);
    EvaluationDto save (EvaluationDto dto);
    EvaluationDto update (EvaluationDto dto);
    void  deleteEvaluation (Long id);

}
