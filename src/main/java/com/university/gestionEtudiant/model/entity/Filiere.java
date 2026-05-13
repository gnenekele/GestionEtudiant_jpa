package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Filiere")
public class Filiere extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private   Long id;
  @Column(nullable = false,unique = true)
  private String sigle;

  @Column(nullable = false,unique = true)
  private String libFiliere;

}
