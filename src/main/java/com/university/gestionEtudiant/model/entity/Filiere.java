package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Filiere")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Filiere extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private   Long id;
  @Column(nullable = false,unique = true)
  private String sigle;

  @Column(nullable = false,unique = true)
  @NotNull(message = "libFiliere est obligatoire")
  private String libFiliere;
  @Column(nullable = false,unique = true)
  private String typeFiliere;
}
