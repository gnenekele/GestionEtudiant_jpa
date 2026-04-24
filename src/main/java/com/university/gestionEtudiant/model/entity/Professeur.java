package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Professeur")
public class Professeur extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idProf;
  private String codeProf;
  private String nomProf;
  private String prenomProf;
  private String matriculeProf;
  private String contactProf;
  private String dateNaissanceProf;
  private String email;
  private String sexeProf;

}
