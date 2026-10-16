package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EmploiDuTemps")
public class EmploiDuTemps extends AuditField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatiere", nullable = false)
    private Matiere matiere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idClasse", nullable = false)
    private  Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProfesseur", nullable = false)
    private  Professeur professeur;


}
