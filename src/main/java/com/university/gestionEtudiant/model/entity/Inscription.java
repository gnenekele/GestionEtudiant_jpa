package com.university.gestionEtudiant.model.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Inscription")
public class Inscription extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime inscriteLe;
  private String inscritePar;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idEtudiant", nullable = false)
  private Etudiant idEtudiant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idAnnee", nullable = false)
  private AnneeAcademique idAnnee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idClasse", nullable = false)
  private  Classe idClasse;



}
