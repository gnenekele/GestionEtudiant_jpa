package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;

public interface AnneeAcademiqueService {
    Page<AnneeAcademique> getAllAnnees (Pageable pageable);
}
