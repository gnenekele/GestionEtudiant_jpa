package com.university.gestionEtudiant.model.mapper;

import com.university.gestionEtudiant.model.dto.ProfesseurDto;
import com.university.gestionEtudiant.model.entity.Professeur;
import org.springframework.stereotype.Component;

@Component
public class ProfesseurMapper {

    // Entité → DTO
    public ProfesseurDto toDto(Professeur entity) {
        if (entity == null) return null;

        return new ProfesseurDto(
                entity.getId(),
                entity.getCodeProf(),
                entity.getNomProf(),
                entity.getPrenomProf(),
                entity.getMatriculeProf(),
                entity.getContactProf(),
                entity.getDateNaissanceProf(),

                entity.getEmail(),
                entity.getSexeProf()
        );
    }

    //DTO → Entité
    public Professeur toEntity(ProfesseurDto dto) {
        if (dto == null) return null;

        Professeur entity = new Professeur();

        // 1. On gère l'ID de manière sécurisée
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        // 2. On utilise les SETTERS pour tous les autres champs
        entity.setId(dto.id());
        entity.setCodeProf(dto.codeProf());
        entity.setNomProf(dto.nomProf());
        entity.setPrenomProf(dto.prenomProf());
        entity.setMatriculeProf(dto.matriculeProf());
        entity.setContactProf(dto.contactProf());
        entity.setDateNaissanceProf(dto.dateNaissanceProf());
        entity.setEmail(dto.email());
        entity.setSexeProf(dto.sexeProf());



        // 3. On retourne l'objet qu'on vient de remplir
        return entity;
    }
}