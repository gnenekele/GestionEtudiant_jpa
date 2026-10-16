package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.InscriptionDto;
import com.university.gestionEtudiant.model.entity.Inscription;
import org.springframework.stereotype.Component;

@Component
public class InscriptionMapper {
    // Entité → DTO

    public InscriptionDto toDto (Inscription entity){
        if (entity == null) return null;

        // Extraction sécurisée de l'ID
        Long etudiantId = null;
        if (entity.getIdEtudiant() != null)
            etudiantId = entity.getIdEtudiant().getId();

        Long anneeId = null;
        if (entity.getIdAnnee() != null){
            anneeId = entity.getIdAnnee().getId();
        }

        Long classeId = null;
        if (entity.getIdClasse()!= null){
            classeId = entity.getIdClasse().getId();
        }

        return new InscriptionDto(
                entity.getId(),
                entity.getInscriteLe(),
                entity.getInscritePar(),
                etudiantId,
                anneeId,
                classeId
        );}

        // DTO → Entité (Optionnel mais recommandé pour les créations/mises à jour)

        public Inscription toEntity (InscriptionDto dto){
            if (dto == null) return null;

            Inscription entity = new Inscription();
            if (dto.id() != null) {
                entity.setId(dto.id());
            }
            entity.setId(dto.id());
            entity.setInscriteLe(dto.inscriteLe());
            entity.setInscritePar(dto.inscritePar());
                return entity;
            }
    }

