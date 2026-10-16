package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository <Etudiant, Long>{
    boolean existsByMatriculeEtudiant(String matricule);
    boolean existsBycodeEtudiant (String code);
    boolean existsByEmailEtudiant (String email);

    boolean existsByMatriculeEtudiantAndIdNot (String matricule,Long id);
    boolean existsBycodeEtudiantAndIdNot (String code,Long id);

    //Récupère le dernier matricule pour une année donnée
    Optional<String> findTopByMatriculeEtudiantStartingWithOrderByMatriculeEtudiantDesc(String prefix);



}
