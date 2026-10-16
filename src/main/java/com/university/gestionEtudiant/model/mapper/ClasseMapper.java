package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.ClasseFormDto;
import com.university.gestionEtudiant.model.dto.ClasseTableDto;
import com.university.gestionEtudiant.model.entity.Classe;
import com.university.gestionEtudiant.model.entity.Filiere;
import com.university.gestionEtudiant.model.entity.Niveau;
import org.springframework.stereotype.Component;

@Component
public class ClasseMapper {

    // Entité → DTO (Affichage)
    public ClasseTableDto toDto(Classe entity) {
        if (entity == null) return null;

        Long filliereId = null;
        if (entity.getFiliere() != null) {
            filliereId = entity.getFiliere().getId();
        }

        Long niveauId = null;
        if (entity.getNiveau() != null) {
            niveauId = entity.getNiveau().getId();
        }

        return new ClasseTableDto(
                entity.getId(),
                entity.getTypeClasse(),
                filliereId,
                niveauId
        );
    }


}
