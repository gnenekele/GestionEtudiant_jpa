package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Niveau")
public class Niveau extends AuditField{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false,unique = true)
  @NotNull(message = "libNiveau est obligatoire")
  private String libNiveau;


}
