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
  private long idInscription;
  private long idEtudiant;
  private long idAnnee;
  private long idClasse;
  private LocalDateTime inscriteLe;
  private String inscritePar;



}
