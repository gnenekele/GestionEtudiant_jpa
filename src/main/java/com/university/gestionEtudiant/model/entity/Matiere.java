package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Matiere")
public class Matiere extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idMatiere;
  private String libMatiere;


}
