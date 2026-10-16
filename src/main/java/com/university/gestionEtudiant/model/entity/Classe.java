package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Classe")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Classe extends AuditField {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String typeClasse;

  //private String effectif;


    @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "id_filiere", nullable = false)
  private Filiere filiere;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_niveau", nullable = false)
  private Niveau niveau;


}