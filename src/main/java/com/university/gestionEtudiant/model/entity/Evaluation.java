package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Evaluation")
public class Evaluation extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "libEvaluation est obligatoire")
  private String libEvaluation;

  @NotNull(message = "dateEvaluation est obligatoire")
  @Column(nullable = false)
  private LocalDateTime dateEvaluation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idPeriode", nullable = false)
  private Periode periode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idMatiere", nullable = false)
  private Matiere matiere;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idClasse", nullable = false)
  private Classe idClasse;
}
