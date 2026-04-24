package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Periode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodeRepository extends JpaRepository<Periode,Long> {
}
