package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
