package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnneeAcademiqueRepository extends JpaRepository <AnneeAcademique, Long>{
    boolean existsByAnnee (String annee);
    boolean existsByAnneeAndIdNot (String annee,Long id);
}
