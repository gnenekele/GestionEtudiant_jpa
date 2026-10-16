package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.dto.ProfesseurFormDto;
import com.university.gestionEtudiant.model.dto.ProfesseurTableDto;
import com.university.gestionEtudiant.model.entity.Etudiant;
import com.university.gestionEtudiant.model.entity.Professeur;
import com.university.gestionEtudiant.model.mapper.ProfesseurMapper;
import com.university.gestionEtudiant.repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfesseurServiceImp implements ProfesseurService {

    private final ProfesseurRepository profRepos;
    private final ProfesseurMapper profMapper;
    private final MatriculeGeneratorService matriculeGenerator;
    private static final Logger log = LogUtils.getLogger(Professeur.class);

    @Override
    public List<ProfesseurTableDto> getAllProf(Pageable pageable) {
        log.info("loading getAllProf page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return profRepos.findAll(pageable).getContent().stream()
                    .map(profMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllProf page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public ProfesseurTableDto getProfById(Long id) {
        log.info("getEtudiantById  for {}", id);
        try {
            Optional<Professeur> data = profRepos.findById(id);
            if (data.isPresent()) {
                return profMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEtudiantById  failed:", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public ProfesseurTableDto save(ProfesseurFormDto dto) {
        log.info("save Prof");
        verifUniqueProf (dto.nomProf(), dto.prenomProf(), dto.email(), null);
        // Génération automatique stricte (plus de vérification du DTO)
        String matriculFinal = matriculeGenerator.genererMatriculeProfesseur();

        try {
            Professeur prof = Professeur.builder()
                    .matriculeProf(matriculFinal)
                    .nomProf(dto.nomProf())
                    .prenomProf(dto.prenomProf())
                    .dateNaissanceProf(dto.dateNaissanceProf())
                    .sexeProf(dto.sexeProf())
                    .contactProf(dto.contactProf())
                    .email(dto.email())
                    .build();
            Professeur profSauvegarde = profRepos.save(prof);
            return profMapper.toDto(profSauvegarde);

        } catch (Exception e) {
            log.error("save Prof failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public ProfesseurTableDto update(Long id, ProfesseurFormDto dto) {
        log.info("update Prof");
        // verification des doubles
        verifUniqueProf (dto.nomProf(), dto.prenomProf(), dto.email(), id);

        String matriculFinal = matriculeGenerator.genererMatriculeProfesseur();

        try {
            Professeur prof = Professeur.builder()
                    .id(id)
                    .matriculeProf(matriculFinal)
                    .nomProf(dto.nomProf())
                    .prenomProf(dto.prenomProf())
                    .dateNaissanceProf(dto.dateNaissanceProf())
                    .sexeProf(dto.sexeProf())
                    .contactProf(dto.contactProf())
                    .email(dto.email())
                    .build();
            Professeur profSauvegarde = profRepos.save(prof);
            return profMapper.toDto(profSauvegarde);
        } catch (Exception e) {
            log.error("update Prof failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public void deleteProf(Long id) {

        // Check if data exists and throw an exception if not found
        Optional<Professeur> data = profRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            profRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    // The `verifUniqueAnnee` function allows you to check if the label exists.
    private void verifUniqueProf(String nomProf,String prenomProf,String email,Long id) {
        boolean exists;
        if (id == null) {
            // Mode AJOUT
            exists = profRepos.existsByNomProfAndPrenomProfAndEmail(nomProf,prenomProf, email);
            }
         else {
                exists = profRepos.existsByNomProfAndPrenomProfAndEmailAndIdNot(nomProf, prenomProf, email, id);

                }
                // 2. Levée de l'exception si le doublon existe
                if (exists) {
                    throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
                }
            }
        }
