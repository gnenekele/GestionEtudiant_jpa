package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Evaluation")
public class Evaluation extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idEvaluation;
  private long idPeriode;
  private long idMatiere;
  private String libEvaluation;
  private LocalDateTime dateEvaluation;





}
