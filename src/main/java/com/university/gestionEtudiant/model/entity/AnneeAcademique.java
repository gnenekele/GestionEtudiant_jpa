package com.university.gestionEtudiant.model.entity;


import com.university.gestionEtudiant.validator.ValidAnneeAcademique;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

@Table(name = "AnneeAcademique")
public class AnneeAcademique extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Votre annotation personnalisée remplace le @Pattern  message = "Le format doit être YYYY-YYYY (ex: 2024-2025)"
  @ValidAnneeAcademique
  @Column(name = "annee", nullable = false, unique = true)
  private String  annee;

  // ✅ Relation inverse : une année a plusieurs périodes
  @OneToMany(mappedBy = "anneeAcademique", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Periode> periodes ;

}
