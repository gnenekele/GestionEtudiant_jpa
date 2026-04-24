package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Periode")
public class Periode extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long idPeriode;
  private long idAnnee;
  private String typePeriode;
  private String dateDeb;
  private String dateFin;




}
