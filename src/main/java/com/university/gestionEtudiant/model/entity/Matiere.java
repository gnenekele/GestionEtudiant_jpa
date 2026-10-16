package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "Matiere")
public class Matiere extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false,unique = true)
  @NotNull(message = "libMatiere est obligatoire")
  private String libMatiere;
  private Long coefficient;


}
