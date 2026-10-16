package com.university.gestionEtudiant.service;


import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.error.Errors;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.dto.ParentTableDto;

import com.university.gestionEtudiant.model.dto.parentFormDto;
import com.university.gestionEtudiant.model.entity.Filiere;
import com.university.gestionEtudiant.model.entity.Parent;
import com.university.gestionEtudiant.model.mapper.ParentMapper;
import com.university.gestionEtudiant.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ParentServiceImpl implements  ParentService {

    private final ParentRepository parentRepos;
    private final ParentMapper parentMapper;
    private static final Logger log = LogUtils.getLogger(Parent.class);

    @Override
    public ParentTableDto getParentById(Long id) {
        log.info("getParentById  for {}", id);
        try {
            Optional<Parent> data = parentRepos.findById(id);
            if (data.isPresent()) {
                return parentMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getParentById  failed:", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

    }


    @Override
    public List<ParentTableDto> getAllParent(Pageable pageable) {
        log.info("loading getAllParent page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return parentRepos.findAll(pageable).getContent().stream()
                    .map(parentMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllParent page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public ParentTableDto save(parentFormDto dto) {
        log.info("save parent");

        verifUniqueProf(dto.nomParent(), dto.prenomParent(), dto.email(), null);

        try {
            return parentMapper.toDto(parentRepos.save(
                    Parent.builder()
                            .nomParent(dto.nomParent())
                            .prenomParent(dto.prenomParent())
                            .contactParent(dto.contactParent())
                            .email(dto.email())
                            .profession(dto.profession())
                            .build()));


        }
        catch (Exception e) {
            log.error("save parent failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }


    @Override
    public ParentTableDto update(Long id, parentFormDto dto) {
        log.info("update parent");
        verifUniqueProf(dto.nomParent(), dto.prenomParent(), dto.email(),id);
        // 1. Récupération et vérification de l'existence (Inversion corrigée)
        Optional<Parent> parent = parentRepos.findById(id);
        if (parent.isEmpty()) {
            throw Errors.notFound(MessageEnum.DATA_NOT_FOUND.getText(), id.toString());
        }

        try {
            // 2. Mise à jour des données (en supposant que dto est un Record Java)
            parent.get().setNomParent(dto.nomParent());
            parent.get().setPrenomParent(dto.prenomParent());
            parent.get().setContactParent(dto.contactParent());
            parent.get().setEmail(dto.email());
            parent.get().setProfession(dto.profession());

            // 3. Sauvegarde et mapping
            return parentMapper.toDto(parentRepos.save(parent.get()));

        } catch (Exception e) {
            log.error("update parent failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }




    @Override
    public void deleteParent(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Parent> data = parentRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            parentRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete deleteParent failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    public void verifUniqueProf(String nomParent, String prenomParent, String email,Long id) {
        // 1. Vérification de l'unicité (Associer la bonne méthode du DTO au bon paramètre)
        boolean exists;
        if (id == null) {
            // Logic for ADD (Add)
            exists = parentRepos.existsByNomParentAndPrenomParentAndEmail(nomParent, prenomParent, email);
        } else {
            exists = parentRepos.existsByNomParentAndPrenomParentAndEmailAndIdNot(nomParent, prenomParent, email, id);

        }
        // 2. Levée de l'exception si le doublon existe
        if (exists) {
            throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
        }
    }
}