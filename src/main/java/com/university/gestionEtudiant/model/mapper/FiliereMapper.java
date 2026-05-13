package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.FiliereDto;
import com.university.gestionEtudiant.model.entity.Filiere;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {
    public FiliereDto toDto(Filiere entity){
        // Entité → DTO
        if (entity == null) return null;

        return new FiliereDto(
                entity.getId(),
                entity.getLibFiliere(),
                entity.getSigle());
    }
    // DTO → Entité
    public Filiere toEntity(FiliereDto dto){
        if (dto == null) return null;
        Filiere entity = new  Filiere();

        // 1. On gère l'ID de manière sécurisée

        if (dto.id()  != null){
            entity.setId(dto.id());
        }
        // 2. On utilise les SETTERS pour tous les autres champs
        //entity.setId(dto.id());
        entity.setSigle(dto.sigle());
        entity.setLibFiliere(dto.libFiliere());

        // 3. On retourne l'objet qu'on vient de remplir
        return entity;
    }
}
