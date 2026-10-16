package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Enseigner")
public class Enseigner extends AuditField {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String codeEnseigner;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idProf", nullable = false)
  private Professeur professeur;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idMatiere", nullable = false)
  private Matiere matiere;

  /**
   * Cette méthode s'exécute automatiquement avant la création (INSERT) en BDD.
   * Elle génère un code unique si celui-ci n'a pas été défini manuellement.
   */
  @PrePersist
  public void generateUniqueCode() {
    if (this.codeEnseigner == null || this.codeEnseigner.isEmpty()) {
      // Exemple 1 : Code basé sur les 8 premiers caractères d'un UUID (ex: ENS-a1b2c3d4)
      String shortId = UUID.randomUUID().toString().substring(0, 8);
      this.codeEnseigner = "PROF-" + shortId;

      // Exemple 2 alternative (si le professeur est déjà chargé) :
      // this.code = "PROF-" + professeur.getId() + "-" + shortId;
    }

  }
}