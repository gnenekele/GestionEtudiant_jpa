package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiveauRepository extends JpaRepository<Niveau,Long> {
}
