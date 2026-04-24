package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasseRepository extends JpaRepository <Etudiant, Long>{}

