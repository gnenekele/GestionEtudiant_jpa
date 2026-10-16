package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.ParentTableDto;
import com.university.gestionEtudiant.model.entity.Parent;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {
    public ParentTableDto toDto (Parent entity){

        return new ParentTableDto(
                entity.getId(),
                entity.getNomParent(),
                entity.getPrenomParent(),
                entity.getContactParent(),
                entity.getEmail(),
                entity.getProfession()
        );
    }

}
