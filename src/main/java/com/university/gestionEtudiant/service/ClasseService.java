package com.university.gestionEtudiant.service;
import com.university.gestionEtudiant.model.dto.ClasseFormDto;
import com.university.gestionEtudiant.model.dto.ClasseTableDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ClasseService {
    List<ClasseTableDto> getAllClasse (Pageable pageable);
    ClasseTableDto getClasseById (Long id);
    ClasseTableDto save (ClasseFormDto dto);
    ClasseTableDto update (Long id,ClasseFormDto dto);
    void  deleteClasse (Long id);


}
