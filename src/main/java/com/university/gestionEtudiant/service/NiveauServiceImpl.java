package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.NiveauDto;
import com.university.gestionEtudiant.model.entity.Niveau;
import com.university.gestionEtudiant.model.mapper.NiveauMapper;
import com.university.gestionEtudiant.repository.NiveauRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NiveauServiceImpl implements NiveauService {
    private final NiveauRepository niveauRepos;
    private final NiveauMapper niveauMapper;
    private static final Logger log = LogUtils.getLogger(Niveau.class);


    @Override
    public List<NiveauDto> getAllNiveau(Pageable pageable) {
        log.info("loading getAllNiveau page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return niveauRepos.findAll(pageable).getContent().stream()
                    .map(niveauMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllNiveau page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public NiveauDto getNiveauById(Long id) {
        log.info("getNiveauById  for {}", id);
        try {
            Optional<Niveau> data = niveauRepos.findById(id);
            if (data.isPresent()) {
                return niveauMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getMatiereById  failed:", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

    }

    @Override
    public NiveauDto save(NiveauDto dto) {
        log.info("save Matiere");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}
        // verification des doubles
        verifUniqueNiveau (dto.libNiveau(), null);
        try {
            return niveauMapper.toDto(niveauRepos.save(niveauMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Matiere failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public NiveauDto update(NiveauDto dto) {
        log.info("update Niveau");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        // verification des doubles
        verifUniqueNiveau(dto.libNiveau(), dto.id());

        try{
            Optional<Niveau> niveau = niveauRepos.findById(dto.id());
            if (niveau.isPresent()){
                return niveauMapper.toDto(niveauRepos.save(niveauMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update Niveau failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }



    @Override
    public void deleteNiveau(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Niveau> data = niveauRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            niveauRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Niveau failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    private void verifUniqueNiveau(String libNiveau, Long id) {
        boolean exists;

        if (id == null) {
            // Logic for ADD (Add)
            exists = niveauRepos.existsByLibNiveau(libNiveau);
        } else {
            exists = niveauRepos.existsByLibNiveauAndIdNot(libNiveau, id);
        }
        if (exists) {
            throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
        }
    }
}