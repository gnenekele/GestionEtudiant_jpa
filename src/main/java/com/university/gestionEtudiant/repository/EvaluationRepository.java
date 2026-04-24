package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
}
