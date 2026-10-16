package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.InscriptionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InscriptionService {
   List<InscriptionDto> getAllInscription (Pageable pageable);
   InscriptionDto getInscriptionById (Long id);
    InscriptionDto save (InscriptionDto dto);
    InscriptionDto update (InscriptionDto dto);
    void deleteInscription(Long id);
}
