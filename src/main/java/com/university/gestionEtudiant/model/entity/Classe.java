package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Classe")
public class Classe extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idClasse;
  private long idFiliere;
  private long idNiveau;
  private String typeClasse;

}
