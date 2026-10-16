package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.AbsenceDto;
import com.university.gestionEtudiant.model.entity.Absence;
import org.springframework.stereotype.Component;

@Component
public class AbsenceMapper {

    public AbsenceDto toDto(Absence entity) {
        // Entité → DTO
        if (entity == null) return null;

        // Extraction sécurisée de l'ID

        Long matiereId = null;
        if (entity.getMatiere() != null) {
            matiereId = entity.getMatiere().getId();
        }

        Long etudiantId = null;
        if (entity.getEtudiant() != null) {
            etudiantId = entity.getEtudiant().getId();
        }

        return new AbsenceDto(
                entity.getId(),
                entity.getDateAbsence(),
                entity.isStatut(),
                entity.getMotif(),
                matiereId,
                etudiantId
        );
    }
    // DTO → Entité (Optionnel mais recommandé pour les créations/mises à jour)

    public Absence toEntity(AbsenceDto dto) {
        if (dto == null) return null;

        Absence entity = new Absence();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setDateAbsence(dto.dateAbsence());
        entity.setStatut(dto.statut());
        entity.setMotif(dto.motif());

        return entity;
    }
}