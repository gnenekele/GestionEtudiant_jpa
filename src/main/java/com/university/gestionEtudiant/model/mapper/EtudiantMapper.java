package com.university.gestionEtudiant.model.mapper;
import com.university.gestionEtudiant.model.dto.EtudiantDto;
import com.university.gestionEtudiant.model.entity.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    // Entité → DTO
    public EtudiantDto toDto(Etudiant entity) {
        if (entity == null) return null;

        return new EtudiantDto(
                entity.getId(),
                entity.getCodeEtudiant(),
                entity.getNomEtudiant(),
                entity.getPrenomEtudiant(),
                entity.getMatriculeEtudiant(),
                entity.getContactEtudiant(),
                entity.getDateNaissEtudiant(),
                entity.getEmailEtudiant(),
                entity.getContactParent(),
                entity.getNomParent(),
                entity.getSexeEtudiant()
        );
    }

    // DTO → Entité
    public Etudiant toEntity(EtudiantDto dto) {
        if (dto == null) return null;

        Etudiant entity = new Etudiant();

        // 1. On gère l'ID de manière sécurisée
        if (dto.id() != null) {
            entity.setId(dto.id());
        }

        // 2. On utilise les SETTERS pour tous les autres champs
        entity.setCodeEtudiant(dto.codeEtudiant());
        entity.setNomEtudiant(dto.nomEtudiant());
        entity.setPrenomEtudiant(dto.prenomEtudiant());
        entity.setMatriculeEtudiant(dto.matriculeEtudiant());
        entity.setContactEtudiant(dto.contactEtudiant());
        entity.setDateNaissEtudiant(dto.dateNaissEtudiant());
        entity.setEmailEtudiant(dto.emailEtudiant());
        entity.setContactParent(dto.contactParent());
        entity.setNomParent(dto.nomParent());
        entity.setSexeEtudiant(dto.sexeEtudiant());

        // 3. On retourne l'objet qu'on vient de remplir
        return entity;
    }
}
