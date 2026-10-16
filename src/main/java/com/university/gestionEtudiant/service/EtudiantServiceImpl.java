package com.university.gestionEtudiant.service;
import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.error.Errors;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;


import com.university.gestionEtudiant.model.dto.EtudiantFormDto;
import com.university.gestionEtudiant.model.dto.EtudiantTableDto;
import com.university.gestionEtudiant.model.entity.Etudiant;
import com.university.gestionEtudiant.model.entity.Filiere;
import com.university.gestionEtudiant.model.entity.Parent;
import com.university.gestionEtudiant.model.mapper.EtudiantMapper;
import com.university.gestionEtudiant.repository.EtudiantRepository;
import com.university.gestionEtudiant.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    // injection des tables
    private final EtudiantRepository etudiantRepos;
    private final ParentRepository parentRepos;
    private final EtudiantMapper etudiantMapper;
    private final MatriculeGeneratorService matriculeGenerator;
    private static final Logger log = LogUtils.getLogger(Service.class);

    @Override
    public List<EtudiantTableDto> getAllEtudiant(Pageable pageable) {
        log.info("loading getAllEtudiant page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return etudiantRepos.findAll(pageable).getContent().stream()
                    .map(etudiantMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllEtudiant page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public EtudiantTableDto getEtudiantById(Long id) {
        log.info("getEtudiantById  for {}",id);
        try {
            Optional<Etudiant> data = etudiantRepos.findById(id);
            if (data.isPresent()) {
                return etudiantMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEtudiantById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public EtudiantTableDto save(EtudiantFormDto dto) {
        log.info("save Etudiant");

        if (dto.idParent() == null) {
            throw new BadRequestException("L'identifiant du parent (idParent) est obligatoire pour inscrire un étudiant.");
        }

        Long parentId = dto.idParent();
        Parent parent = parentRepos.findById(parentId) .orElseThrow(() -> new BadRequestException("Le parent avec l'identifiant " + parentId + " n'existe pas."));

        // Génération automatique stricte (plus de vérification du DTO)
        String matriculeFinal = matriculeGenerator.genererMatricule();
        String codeFinal = matriculeGenerator.genererCodeEtudiant(dto.nomEtudiant(), parent.getPrenomParent());

        // Validation d'unicité avec les valeurs générées
        verifUniqueEtudiant(codeFinal, matriculeFinal, dto.emailEtudiant(), null);

        try {
            Etudiant etudiant = Etudiant.builder()
                    .matriculeEtudiant(matriculeFinal)
                    .codeEtudiant(codeFinal)
                    .nomEtudiant(dto.nomEtudiant())
                    .prenomEtudiant(dto.prenomEtudiant())
                    .dateNaissEtudiant(dto.dateNaissEtudiant())
                    .sexeEtudiant(dto.sexeEtudiant())
                    .contactEtudiant(dto.contactEtudiant())
                    .emailEtudiant(dto.emailEtudiant())
                    .Parent(parent)
                    .build();

            Etudiant etudiantSauvegarde = etudiantRepos.save(etudiant);
            return etudiantMapper.toDto(etudiantSauvegarde);
        } catch (Exception e) {
            log.error("save Etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public EtudiantTableDto update(Long id, EtudiantFormDto dto) {
        log.info("update etudiant");

        // 1. Récupérer l'étudiant existant
        Etudiant existing = etudiantRepos.findById(id) .orElseThrow(() -> Errors.notFound(MessageEnum.DATA_NOT_FOUND.getText(), id.toString()));

        // 2. Récupération des valeurs existantes (Immuables lors d'une mise à jour)
        String matriculeFinal = existing.getMatriculeEtudiant();
        String codeFinal = existing.getCodeEtudiant();

        // 3. Validation d'unicité (l'ID permet d'exclure l'étudiant actuel de la vérification)
        verifUniqueEtudiant(codeFinal, matriculeFinal, dto.emailEtudiant(), id);

        // 4. Gestion du Parent associé
        Parent parentFinal = existing.getParent();
        if (dto.idParent() != null && (parentFinal == null || !dto.idParent().equals(parentFinal.getId()))) {
            parentFinal = parentRepos.findById(dto.idParent()) .orElseThrow(() -> new BadRequestException("Le parent avec l'identifiant " + dto.idParent() + " n'existe pas."));
        }

        try {
            // 5. Reconstruction de l'entité avec préservation du matricule et du code
            Etudiant etudiantEntity = Etudiant.builder()
                    .id(id)
                    .matriculeEtudiant(matriculeFinal)
                    .codeEtudiant(codeFinal)
                    .nomEtudiant(dto.nomEtudiant())
                    .prenomEtudiant(dto.prenomEtudiant())
                    .dateNaissEtudiant(dto.dateNaissEtudiant())
                    .sexeEtudiant(dto.sexeEtudiant())
                    .contactEtudiant(dto.contactEtudiant())
                    .emailEtudiant(dto.emailEtudiant())
                    .Parent(parentFinal)
                    .build();

            Etudiant etudiantSauvegarde = etudiantRepos.save(etudiantEntity);
            return etudiantMapper.toDto(etudiantSauvegarde);
        } catch (Exception e) {
            log.error("update etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }






    @Override
    public void deleteEtudiant(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Etudiant> data = etudiantRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            etudiantRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    // The `verifUniqueAnnee` function allows you to check if the label exists.
    private void verifUniqueEtudiant(String code, String matricule,String email,Long id) {
        if (id == null) {
            // Mode AJOUT
            if (etudiantRepos.existsBycodeEtudiant(code)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
            if (etudiantRepos.existsByMatriculeEtudiant(matricule)) {
                throw new BadRequestException(ErrorCode.CONFLICT, "Ce matricule existe déjà");
            }
            if ( etudiantRepos.existsByEmailEtudiant(email)) {
                throw new BadRequestException("Cette adresse email est déjà utilisée par un autre étudiant.");
            }
        } else {
            // Mode MODIFICATION
            if (etudiantRepos.existsBycodeEtudiantAndIdNot(code, id)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
            if (etudiantRepos.existsByMatriculeEtudiantAndIdNot(matricule, id)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
        }
    }

}

