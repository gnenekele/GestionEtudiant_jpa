package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "parent", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_parent_nom_prenom_email",
                columnNames = {"nomParent", "prenomParent", "email"}
        )
})

public class Parent extends AuditField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nomParent;
    private String prenomParent;
    private String contactParent;
    private String email;
    private String profession;

}


