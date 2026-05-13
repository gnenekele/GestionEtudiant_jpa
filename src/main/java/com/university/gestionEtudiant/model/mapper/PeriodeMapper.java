package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.model.entity.Periode;
import org.springframework.stereotype.Component;

@Component
public class PeriodeMapper {

    // Entité → DTO
    public PeriodeDto toDto(Periode entity) {
        if (entity == null) return null;

        // Extraction sécurisée de l'ID de l'année académique
        Long anneeId = null;
        if (entity.getAnneeAcademique() != null) {
            anneeId = entity.getAnneeAcademique().getId();
        }

        return new PeriodeDto(
                entity.getId(),
                entity.getTypePeriode(),
                entity.getDateDeb(),
                entity.getDateFin(),
                anneeId // Ajout de l'ID de l'année au constructeur du DTO
        );
    }

    // DTO → Entité (Optionnel mais recommandé pour les créations/mises à jour)
    public Periode toEntity(PeriodeDto dto) {
        if (dto == null) return null;

        Periode entity = new Periode();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setId(dto.id());
        entity.setTypePeriode(dto.typePeriode());
        entity.setDateDeb(dto.dateDeb());
        entity.setDateFin(dto.dateFin());

        // Note : L'association de l'objet AnneeAcademique complet
        // se fera dans votre classe Service à l'aide de l'anneeId.

        return entity;
    }
}
