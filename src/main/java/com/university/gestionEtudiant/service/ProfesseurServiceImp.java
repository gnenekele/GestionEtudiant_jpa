package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.ProfesseurDto;
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
public class ProfesseurServiceImp implements ProfesseurService{

    private  final ProfesseurRepository profRepos;
    private final ProfesseurMapper profMapper;
    private static final Logger log = LogUtils.getLogger(Professeur.class);

    @Override
    public List<ProfesseurDto> getAllProf(Pageable pageable) {
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
    public ProfesseurDto getProfById(Long id) {
        log.info("getEtudiantById  for {}",id);
        try {
            Optional<Professeur> data = profRepos.findById(id);
            if (data.isPresent()) {
                return profMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEtudiantById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public ProfesseurDto save(ProfesseurDto dto) {
        log.info("save Prof");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}
        // verification des doubles
        verifUniqueProf (dto.codeProf(), dto.matriculeProf(), null);
        try {
            return profMapper.toDto(profRepos.save(profMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Prof failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public ProfesseurDto update(ProfesseurDto dto) {
        log.info("update Prof");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        // verification des doubles
        verifUniqueProf (dto.codeProf(), dto.matriculeProf(), dto.id());
        try{
            Optional<Professeur> etudiant = profRepos.findById(dto.id());
            if (etudiant.isPresent()){
                return profMapper.toDto(profRepos.save(profMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update Prof failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
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
    private void verifUniqueProf(String code, String matriculeProf, Long id) {
        if (id == null) {
            // Mode AJOUT
            if (profRepos.existsByCodeProf(code)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
            if (profRepos.existsByMatriculeProf(matriculeProf)) {
                throw new BadRequestException(ErrorCode.CONFLICT, "Ce matricule existe déjà");
            }
        } else {
            // Mode MODIFICATION
            if (profRepos.existsByCodeProfAndIdNot(code, id)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
            if (profRepos.existsByMatriculeProfAndIdNot(matriculeProf, id)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }

        }
    }
}