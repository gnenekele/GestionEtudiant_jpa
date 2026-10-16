package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.AbsenceDto;
import com.university.gestionEtudiant.model.dto.EmploiDuTempsDto;
import com.university.gestionEtudiant.model.entity.Absence;
import com.university.gestionEtudiant.model.entity.EmploiDuTemps;
import org.springframework.stereotype.Component;

@Component
public class EmploiDuTempsMapper {
    public EmploiDuTempsDto toDto (EmploiDuTemps entity){
        // Entité → DTO
        if (entity == null) return null;

        // Extraction sécurisée de l'ID

        Long matiereId = null;
        if (entity.getMatiere() != null) {
            matiereId = entity.getMatiere().getId();
        }
        Long classeId = null;
        if (entity.getClasse() != null) {
            classeId = entity.getClasse().getId();
        }

        Long professeurId =null;
        if (entity.getProfesseur() != null) {
            professeurId = entity.getProfesseur().getId();
        }
        return new EmploiDuTempsDto(
                entity.getId(),
                entity.getJour(),
                entity.getHeureDebut(),
                entity.getHeureFin(),
                entity.getSalle(),
                matiereId,
                classeId,
                professeurId
        );
    }
    // DTO → Entité (Optionnel mais recommandé pour les créations/mises à jour)

    public EmploiDuTemps toEntity(EmploiDuTempsDto dto) {
        if (dto == null) return null;

        EmploiDuTemps entity = new EmploiDuTemps();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setJour(dto.jour());
        entity.setHeureDebut(dto.heureDebut());
        entity.setHeureFin(dto.heureFin());
        entity.setSalle(dto.salle());

        return entity;
    }
}
