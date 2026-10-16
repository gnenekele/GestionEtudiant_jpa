package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.error.Errors;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.FiliereFormDto;
import com.university.gestionEtudiant.model.dto.FiliereTableDto;

import com.university.gestionEtudiant.model.entity.Filiere;
import com.university.gestionEtudiant.model.mapper.FiliereMapper;
import com.university.gestionEtudiant.repository.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FiliereServiceImp implements FiliereService{

    private final FiliereRepository filiereRepos;
    private final FiliereMapper filiereMapper;
    private static final Logger log = LogUtils.getLogger(Filiere.class);


    @Override
    public List<FiliereTableDto> getAllFiliere(Pageable pageable) {
        log.info("loading getAllFiliere page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return filiereRepos.findAll(pageable).getContent().stream()
                    .map(filiereMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllFilier page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }



    @Override
    public FiliereTableDto getFiliereById(Long id) {

        log.info("getFiliereById  for {}",id);
        try {
            Optional<Filiere> data = filiereRepos.findById(id);
            if (data.isPresent()) {
                return filiereMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getFiliereById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }




    @Override
    public FiliereTableDto save(FiliereFormDto dto) {
        log.info("save Filiere");

        // 1. Vérification des doublons au moment de la création
        verifUniqueFiliere(dto.libFiliere(), null);

        try {
            // 2. Conversion et sauvegarde directe de l'entité
            return filiereMapper.toDto(filiereRepos.save(
                    Filiere.builder()
                            .typeFiliere(dto.typeFiliere())
                            .libFiliere(dto.libFiliere())
                            .sigle(dto.sigle())
                            .build()
            ));

        } catch (Exception e) {
            log.error("save Filiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public FiliereTableDto update(Long id,FiliereFormDto dto) {
        log.info("update filiere");

        Optional<Filiere> existing = filiereRepos.findById(id);
        if (existing.isEmpty()) {
            throw Errors.notFound(MessageEnum.DATA_NOT_FOUND.getText(), id.toString());
        }

        verifUniqueFiliere(dto.libFiliere(), id);

        try {
           existing.get().setLibFiliere(dto.libFiliere());
           existing.get().setSigle(dto.sigle());
           existing.get().setTypeFiliere(dto.typeFiliere());
           return filiereMapper.toDto(filiereRepos.save(existing.get()));
        } catch (Exception e) {
            log.error("update filiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public void deleteFiliere(Long id) {
// Check if data exists and throw an exception if not found
        Optional<Filiere> data = filiereRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            filiereRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Filiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
        // The `verifUniqueAnnee` function allows you to check if the label exists.
        private void verifUniqueFiliere(String libFiliere, Long id) {
            boolean exists;

            if (id == null) {
                // Logic for ADD (Add)
                exists =filiereRepos.existsByLibFiliere(libFiliere);
            } else {
                // Logique pour la MODIFICATION (Update)
                exists = filiereRepos.existsByLibFiliereAndIdNot(libFiliere, id);
            }

            if (exists) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
        }
}
