package com.university.gestionEtudiant.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Professeur")
public class Professeur extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false,unique = true)
  private String codeProf;

  private String nomProf;

  private String prenomProf;
  @Column(nullable = false,unique = true)

  private String matriculeProf;
  private String contactProf;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date dateNaissanceProf;

  private String email;
  private String sexeProf;

}
