package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EtudiantEtudiant")
public class Etudiant extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private long idEtudiant;
  private String codeEtudiant;
  private String nomEtudiant;
  private String prenomEtudiant;
  private String matriculeEtudiant;
  private String contactEtudiant;
  private String dateNaissEtudiant;
  private String emailEtudiant;
  private String contactParent;
  private String nomParent;
  private String sexeEtudiant;

}
