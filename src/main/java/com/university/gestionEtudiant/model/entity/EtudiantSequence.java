package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etudiant_sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annee")
    private Integer annee;

   @Builder.Default
    @Column(name = "dernier_numero", nullable = false)
    private Integer dernierNumero = 0;
}