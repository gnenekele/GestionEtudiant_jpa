package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.EnseignerTableDto;
import com.university.gestionEtudiant.model.entity.Enseigner;
import org.springframework.stereotype.Component;

@Component
public class EnseignerMapper {
    public EnseignerTableDto toDto (Enseigner entity){
        if (entity == null) return null;

        // Extraction sécurisée de l'ID
        Long matiereId = null;
        if (entity.getMatiere()!= null){
            matiereId = entity.getMatiere().getId();
        }

        Long professeurId = null;
        if (entity.getProfesseur() != null){
                professeurId=  entity.getProfesseur().getId();}

        return new EnseignerTableDto(
                entity.getId(),
                entity.getCodeEnseigner(),
                professeurId,
                matiereId

        );

    }
}
