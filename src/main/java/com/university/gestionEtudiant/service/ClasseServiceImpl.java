package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.error.Errors;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.api.ResourceNotFoundException;
import com.university.gestionEtudiant.model.dto.ClasseFormDto;
import com.university.gestionEtudiant.model.dto.ClasseTableDto;
import com.university.gestionEtudiant.model.entity.*;
import com.university.gestionEtudiant.model.mapper.ClasseMapper;
import com.university.gestionEtudiant.repository.ClasseRepository;
import com.university.gestionEtudiant.repository.FiliereRepository;
import com.university.gestionEtudiant.repository.NiveauRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClasseServiceImpl implements ClasseService{
    public final FiliereRepository filiereRepos;
    public final NiveauRepository niveauRepos;
    public final ClasseRepository classeRepos;
    public final ClasseMapper classeMapper;
    private static final Logger log = LogUtils.getLogger(Classe.class);


    @Override
    public List<ClasseTableDto> getAllClasse(Pageable pageable) {
        log.info("loading getAllClasse page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
       try {
           return classeRepos.findAll(pageable).getContent().stream()
                   .map(classeMapper::toDto)
                   .toList();
       }catch (Exception e) {
           log.error("loading getAllClasse page failed: ", e);
           throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
       }

    }

    @Override
    public ClasseTableDto getClasseById(Long id) {
        log.info("getClasseById  for {}",id);
        try {
            Optional<Classe> data = classeRepos.findById(id);
            if (data.isPresent()) {
                return classeMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getClasseById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }

    @Override
    public ClasseTableDto save(ClasseFormDto dto) {
        log.info("save classe");

        try{
            // Récupérer les entités référencées par leurs IDs
            Filiere filiere = filiereRepos.findById(dto.idFiliere())
                    .orElseThrow(() -> Errors.notFound(
                            MessageEnum.DATA_NOT_FOUND.getText(),
                            "Filiere id: " + dto.idFiliere()));

            Niveau niveau = niveauRepos.findById(dto.idNiveau())
                    .orElseThrow(() -> Errors.notFound(
                            MessageEnum.DATA_NOT_FOUND.getText(),
                            "Niveau id: " + dto.idNiveau()));

            // Construire et sauvegarder l'entité Classe avec les relations
            Classe classe = Classe.builder()
                    .typeClasse(dto.typeClasse())
                    .filiere(filiere)
                    .niveau(niveau)
                    .build();

            return classeMapper.toDto(classeRepos.save(classe));

        } catch (Exception e) {
            log.error("save classe failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public ClasseTableDto update(Long id, ClasseFormDto dto) {
        log.info("Mise à jour de la Classe avec l'id : {}", id);

        // 1. Récupérer l'entité existante (et non le DTO) depuis la base de données
        Classe existing = classeRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe non trouvée avec l'id: " + id));

        // 2. Vérifier et récupérer les nouvelles dépendances
        Filiere filiere = filiereRepos.findById(dto.idFiliere())
                .orElseThrow(() -> new ResourceNotFoundException("Filiere non trouvée avec l'id: " + dto.idFiliere()));

        Niveau niveau = niveauRepos.findById(dto.idNiveau())
                .orElseThrow(() -> new ResourceNotFoundException("Niveau non trouvé avec l'id: " + dto.idNiveau()));

        // 3. Mettre à jour les champs de l'entité avec les données du DTO
        existing.setTypeClasse(dto.typeClasse());
        existing.setFiliere(filiere);
        existing.setNiveau(niveau);
        // Ajoutez ici d'autres setters si vous avez d'autres champs à modifier (ex: libelle)

        // 4. Sauvegarder l'entité mise à jour
        Classe savedClasse = classeRepos.save(existing);

        // 5. Convertir l'entité sauvegardée en DTO de retour
        return classeMapper.toDto(savedClasse);
    }



    @Override
    public void deleteClasse(Long id) {
        Optional<Classe> data = classeRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            classeRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

}
