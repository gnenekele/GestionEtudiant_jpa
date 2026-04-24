package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription,Long> {
}
