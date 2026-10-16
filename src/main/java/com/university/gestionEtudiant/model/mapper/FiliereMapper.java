package com.university.gestionEtudiant.model.mapper;
import com.university.gestionEtudiant.model.dto.FiliereTableDto;
import com.university.gestionEtudiant.model.entity.Filiere;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {

    public FiliereTableDto toDto(Filiere entity) {
        return new FiliereTableDto(
                entity.getId(),
                entity.getLibFiliere(),
                entity.getTypeFiliere(),
                entity.getSigle());


    }
}
