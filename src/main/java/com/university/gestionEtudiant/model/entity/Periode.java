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
@ValidPeriode
@Table(
        name = "Periode",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_periode_unique",
                        columnNames = {"typePeriode", "dateDeb", "dateFin", "idAnnee"}    // Contrainte combinée d'unicité
                )
        })
public class Periode extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "typePeriode est obligatoire")
  private String typePeriode;

  @NotNull(message = "dateDeb est obligatoire")
  @Column(nullable = false)
  private LocalDate dateDeb;

  @NotNull(message = "dateFin est obligatoire")
  @Column(nullable = false)
  private LocalDate dateFin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idAnnee", nullable = false)
  private AnneeAcademique anneeAcademique;
}
