package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.EmploiDuTemps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploiDuTempRepository extends JpaRepository<EmploiDuTemps, Long> {
}
