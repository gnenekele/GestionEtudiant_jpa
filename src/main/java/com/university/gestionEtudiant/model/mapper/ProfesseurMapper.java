package com.university.gestionEtudiant.model.mapper;
import com.university.gestionEtudiant.model.dto.ProfesseurTableDto;
import com.university.gestionEtudiant.model.entity.Professeur;
import org.springframework.stereotype.Component;

@Component
public class ProfesseurMapper {

    // Entité → DTO
    public ProfesseurTableDto toDto(Professeur entity) {
        if (entity == null) return null;

        return new ProfesseurTableDto(
                entity.getId(),
                entity.getMatriculeProf(),
//                entity.getCodeProf(),
                entity.getNomProf(),
                entity.getPrenomProf(),
                entity.getSexeProf(),
                entity.getContactProf(),
                entity.getDateNaissanceProf(),
                entity.getEmail()

        );
    }

}