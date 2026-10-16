package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.MatiereTableDto;
import com.university.gestionEtudiant.model.entity.Matiere;
import org.springframework.stereotype.Component;

@Component
public class MatiereMapper {
    public MatiereTableDto toDto (Matiere entity){
        return new MatiereTableDto(
                entity.getId(),
                entity.getLibMatiere(),
                entity.getCoefficient()
        );
    }

}
