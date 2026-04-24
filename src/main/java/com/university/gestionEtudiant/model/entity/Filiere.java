package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Filiere")
public class Filiere extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idFiliere;
  private String libFiliere;

}
