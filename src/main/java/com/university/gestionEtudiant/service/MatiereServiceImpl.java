package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.MatiereDto;
import com.university.gestionEtudiant.model.entity.Matiere;
import com.university.gestionEtudiant.model.mapper.MatiereMapper;

import com.university.gestionEtudiant.repository.MatiereRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatiereServiceImpl implements MatiereService{
    private final MatiereRepository matiereRepos;
    private final MatiereMapper matiereMapper;
    private static final Logger log = LogUtils.getLogger(Matiere.class);


    @Override
    public MatiereDto getMatiereById(Long id) {
        log.info("getMatiereById  for {}",id);
        try {
            Optional<Matiere> data = matiereRepos.findById(id);
            if (data.isPresent()) {
                return matiereMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getMatiereById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }

    @Override
    public List<MatiereDto> getAllMatiere(Pageable pageable) {
        log.info("loading getAllMatiere page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return matiereRepos.findAll(pageable).getContent().stream()
                    .map(matiereMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllMatiere page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public MatiereDto save(MatiereDto dto) {
        log.info("save Matiere");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}
        // verification des doubles
        verifUniqueMatiere (dto.libMatiere(), null);
        try {
            return matiereMapper.toDto(matiereRepos.save(matiereMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Matiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public MatiereDto update(MatiereDto dto) {
        log.info("update Matiere");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        // verification des doubles
        verifUniqueMatiere(dto.libMatiere(), dto.id());

        try{
            Optional<Matiere> matiere = matiereRepos.findById(dto.id());
            if (matiere.isPresent()){
                return matiereMapper.toDto(matiereRepos.save(matiereMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update Matiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public void deleteMatiere(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Matiere> data = matiereRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            matiereRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Matiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
        // The `verifUniqueAnnee` function allows you to check if the label exists.
        private void verifUniqueMatiere(String libMatiere, Long id) {
            boolean exists;

            if (id == null) {
                // Logic for ADD (Add)
                exists =matiereRepos.existsByLibMatiere(libMatiere);
            } else {
                // Logique pour la MODIFICATION (Update)
                exists = matiereRepos.existsByLibMatiereAndIdNot(libMatiere, id);
            }

            if (exists) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
        }
    }

