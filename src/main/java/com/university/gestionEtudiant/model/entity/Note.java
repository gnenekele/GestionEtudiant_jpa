package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Note")
public class Note extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String libNote;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idEvaluation", nullable = false)
  private Evaluation evaluation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idEtudiant", nullable = false)
  private Etudiant etudiant;
}
