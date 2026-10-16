package com.university.gestionEtudiant.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Professeur", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_parent_nom_prenom_email",
                columnNames = { "nomProf","prenomProf","email"}
        )
})
public class Professeur extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column(nullable = false,unique = true)
   private String matriculeProf;

//  @Column(nullable = false,unique = true)
//  private String codeProf;

  private String nomProf;
  private String prenomProf;
  private String sexeProf;
  private String contactProf;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date dateNaissanceProf;
  private String email;





}
