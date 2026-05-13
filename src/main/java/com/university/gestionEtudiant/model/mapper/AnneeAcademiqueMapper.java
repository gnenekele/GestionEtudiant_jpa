package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.AnneeDto;
import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnneeAcademiqueMapper {

    // Entité → DTO (pour les réponses GET)
    public AnneeDto toDto(AnneeAcademique entity) {
        if (entity == null) return null;

        return new AnneeDto(entity.getId(), entity.getAnnee());
    }

    // DTO → Entité (pour créer ou modifier)
    public AnneeAcademique toEntity(AnneeDto dto) {

        return new AnneeAcademique(dto.id(),dto.annee(),null);
    }

//    // Liste → Liste de DTOs
//    public List<AnneeDto> toDtoList(List<AnneeAcademique> entities) {
//        if (entities == null) return null;
//        return entities.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
}