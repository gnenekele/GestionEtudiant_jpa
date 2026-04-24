package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Enseigner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignerRepository extends JpaRepository<Enseigner,Long> {
}
