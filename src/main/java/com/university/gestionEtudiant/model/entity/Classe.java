package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "Classe")
public class Classe extends AuditField {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idClasse;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idFiliere", nullable = false)
  private Filiere filiere;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idNiveau", nullable = false)
  private Niveau niveau;

  @NotBlank
  private String typeClasse;
}