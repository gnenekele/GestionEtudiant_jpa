package com.university.gestionEtudiant.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EtudiantEtudiant")
public class Etudiant extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;
  @Column(nullable = false,unique = true)
  private String codeEtudiant;
  private String nomEtudiant;
  private String prenomEtudiant;
 @Column(nullable = false,unique = true)
  private String matriculeEtudiant;
    @JsonFormat(pattern = "yyyy-MM-dd")
  private String contactEtudiant;
  private String dateNaissEtudiant;
  private String emailEtudiant;
  private String contactParent;
  private String nomParent;
  private String sexeEtudiant;

}
