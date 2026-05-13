package com.university.gestionEtudiant.model.mapper;
import com.university.gestionEtudiant.model.dto.NiveauDto;
import com.university.gestionEtudiant.model.entity.Niveau;
import org.springframework.stereotype.Component;

@Component
public class NiveauMapper {

    public NiveauDto toDto (Niveau entity){
        // Entité → DTO
        if (entity == null) return null;
        return  new NiveauDto(
                entity.getId(),
                entity.getLibNiveau());
    }
    // DTO → Entité
    public Niveau toEntity (NiveauDto dto){
        if (dto == null) return null;
        Niveau entity = new  Niveau();

        // 1. On gère l'ID de manière sécurisée

        if (dto.id()  != null){
            entity.setId(dto.id());
        }
        // 2. On utilise les SETTERS pour tous les autres champs
        entity.setLibNiveau(dto.libNiveau());
        // 3. On retourne l'objet qu'on vient de remplir
        return entity;
    }
}
