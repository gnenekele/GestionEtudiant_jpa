package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRepository extends JpaRepository<Matiere,Long> {

    boolean existsByLibMatiere (String libFiliere);
    boolean existsByLibMatiereAndIdNot (String libFiliere,Long id);

}
