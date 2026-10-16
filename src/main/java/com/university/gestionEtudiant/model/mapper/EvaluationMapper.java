package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.EvaluationDto;
import com.university.gestionEtudiant.model.entity.Evaluation;
import org.springframework.stereotype.Component;

@Component
public class EvaluationMapper {
    // Entité → DTO

    public EvaluationDto toDto(Evaluation entity) {
        if (entity == null) return null;

        // Extraction sécurisée de l'ID
        Long periodeId = null;
        if (entity.getPeriode() != null) {
            periodeId = entity.getPeriode().getId();
        }
        Long matiereId = null;
        if (entity.getMatiere() != null) {
            matiereId = entity.getMatiere().getId();
        }
        return new EvaluationDto(
                entity.getId(),
                entity.getLibEvaluation(),
                entity.getDateEvaluation(),
                periodeId,
                matiereId
        );
    }

    // DTO → Entité (Optionnel mais recommandé pour les créations/mises à jour)
    public Evaluation toEntity(EvaluationDto dto) {
        if (dto == null) return null;

        Evaluation entity = new Evaluation();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setId(dto.id());
        entity.setLibEvaluation(dto.libEvaluation());
        entity.setDateEvaluation(dto.dateEvaluation());

        return entity;
    }
}