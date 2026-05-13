package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.MatiereDto;
import com.university.gestionEtudiant.model.entity.Matiere;
import org.springframework.stereotype.Component;

@Component
public class MatiereMapper {
    public MatiereDto toDto (Matiere entity){
        // Entité → DTO
        if (entity == null) return null;
        return new MatiereDto(
                entity.getId(),
                entity.getLibMatiere());
    }
    // DTO → Entité
    public Matiere toEntity (MatiereDto dto){
        if (dto == null) return null;
        Matiere entity = new  Matiere();

        // 1. On gère l'ID de manière sécurisée

        if (dto.id()  != null){
            entity.setId(dto.id());
        }
        // 2. On utilise les SETTERS pour tous les autres champs
        entity.setLibMatiere(dto.libMatiere());
        // 3. On retourne l'objet qu'on vient de remplir
        return entity;
    }
}
