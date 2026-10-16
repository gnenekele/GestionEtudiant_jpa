package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.NoteDto;
import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import com.university.gestionEtudiant.model.entity.Note;
import com.university.gestionEtudiant.model.entity.Periode;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    // Entité → DTO
    public NoteDto toDto(Note entity) {
        if (entity == null) return null;

        Long evaluatioId = null;
        if (entity.getEvaluation() != null) {
            evaluatioId = entity.getEvaluation().getId();
        }

        Long etudiantId = null;
        if (entity.getEtudiant() != null) {
            etudiantId = entity.getEtudiant().getId();
        }

        return new NoteDto(
                entity.getId(),
                entity.getLibNote(),
                evaluatioId,
                etudiantId
        );
    }

    // DTO → Entité
    public Note toEntity(NoteDto dto) {
        if (dto == null) return null;

        Note entity = new Note();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setLibNote(dto.libNote());
        // Note: L'association des entités liées (Evaluation/Etudiant)
        // se fait généralement dans la couche Service via leurs repositories respectifs.
        return entity;
    }

    // --- MAPPING PERIODE ---

    // Entité → DTO (Lecture)
    public PeriodeDto toDto(Periode entity) {
        if (entity == null) return null;

        Long idAnnee = null;
        if (entity.getAnneeAcademique() != null) {
            idAnnee = entity.getAnneeAcademique().getId();
        }

        // Utilisation du constructeur canonique du record (sans les 'get')
        return new PeriodeDto(
                entity.getId(),
                entity.getTypePeriode(),
                entity.getDateDeb(),
                entity.getDateFin(),
                idAnnee
        );
    }

    // DTO → Entité (Enregistrement et Modification)
    public Periode toEntity(PeriodeDto dto) {
        if (dto == null) return null;

        Periode entity = new Periode();

        // Accès aux données via la syntaxe du record : dto.nomDuChamp()
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setTypePeriode(dto.typePeriode());
        entity.setDateDeb(dto.dateDeb());
        entity.setDateFin(dto.dateFin());

        // Liaison de la relation avec l'Année Académique
        if (dto.idAnnee() != null) {
            AnneeAcademique annee = new AnneeAcademique();
            annee.setId(dto.idAnnee());
            entity.setAnneeAcademique(annee);
        }

        return entity;
    }
}
