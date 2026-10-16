package com.university.gestionEtudiant.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Etudiant")
public class Etudiant extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 20)
  private String matriculeEtudiant;

  @Column(nullable = false, length = 10)
  private String codeEtudiant;

  @Column(nullable = false, length = 30)
  private String nomEtudiant;

  @Column(nullable = false, length = 100)
  private String prenomEtudiant;

  @Column(nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private String dateNaissEtudiant;

  @Column(nullable = false, length = 1)
  private String sexeEtudiant;

  @Column(nullable = false, length = 30)
  private String contactEtudiant;

  @Column(nullable = false, length = 100,unique = true)
  private String emailEtudiant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idParent", nullable = false)
  private  Parent Parent;

   // private String contactParent;
 // private String nomParent;


}
