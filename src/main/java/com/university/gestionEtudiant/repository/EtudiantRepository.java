package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository <Etudiant, Long>{
    boolean existsByMatriculeEtudiant(String matricule);
    boolean existsBycodeEtudiant (String code);
    boolean existsByMatriculeEtudiantAndIdNot (String matricule,Long id);
    boolean existsBycodeEtudiantAndIdNot (String code,Long id);


}
