package com.university.gestionEtudiant.model.entity;

import com.university.gestionEtudiant.validator.ValidPeriode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ValidPeriode
@Table(name = "Periode")
public class Periode extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  //@NotNull(message = "contactProf est idAnnee")
  //private long idAnnee;
  @NotBlank(message = "typePeriode est obligatoire")
  private String typePeriode;
  @NotNull(message = "dateDeb est obligatoire")
  @Column(nullable = false,unique = true)
  private LocalDate dateDeb;
  @NotNull(message = "dateFin est obligatoire")
  @Column(nullable = false,unique = true)
  private LocalDate dateFin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idAnnee", nullable = false)
  private AnneeAcademique anneeAcademique;
}
