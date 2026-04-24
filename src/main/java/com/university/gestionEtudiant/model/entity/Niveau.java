package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Niveau")
public class Niveau extends AuditField{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idNiveau;
  private String libNiveau;


}
