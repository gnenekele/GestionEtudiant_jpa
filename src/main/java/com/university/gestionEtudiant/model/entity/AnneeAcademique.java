package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AnneeAcademique")
public class AnneeAcademique extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idAnnee;
  private String annee;



}
