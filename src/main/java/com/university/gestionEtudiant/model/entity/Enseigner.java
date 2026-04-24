package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Enseigner")
public class Enseigner extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idEnseigner;
  private long idProf;
  private long idAnnee;
  private long idClasse;
  private long idMatiere;
  private String typeEnseigner;

}
