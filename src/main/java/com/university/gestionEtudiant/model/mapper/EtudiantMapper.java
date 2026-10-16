package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.EtudiantTableDto;
import com.university.gestionEtudiant.model.entity.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    // Entité → DTO
    public EtudiantTableDto toDto(Etudiant entity) {
        if (entity == null) return null;

        // Extraction sécurisée de l'ID
        Long parentId = null;
        if (entity.getParent() != null) {
            parentId = entity.getParent().getId();
        }
        return new EtudiantTableDto(
                entity.getId(),
                entity.getMatriculeEtudiant(),
                entity.getCodeEtudiant(),
                entity.getNomEtudiant(),
                entity.getPrenomEtudiant(),
                entity.getDateNaissEtudiant(),
                entity.getSexeEtudiant(),
                entity.getContactEtudiant(),
                entity.getEmailEtudiant(),
                parentId

                // entity.getContactParent(),
                // entity.getNomParent(),
        );
    }

}
