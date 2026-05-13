package com.university.gestionEtudiant.service;
import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EtudiantDto;

import com.university.gestionEtudiant.model.entity.Etudiant;
import com.university.gestionEtudiant.model.mapper.EtudiantMapper;
import com.university.gestionEtudiant.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepos;
    private final EtudiantMapper etudiantMapper;
    private static final Logger log = LogUtils.getLogger(Service.class);

    @Override
    public List<EtudiantDto> getAllEtudiant(Pageable pageable) {
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
    public EtudiantDto getEtudiantById(Long id) {
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
    public EtudiantDto save(EtudiantDto dto) {
        log.info("save Etudiant");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}
        // verification des doubles
        verifUniqueEtudiant (dto.codeEtudiant(), dto.matriculeEtudiant(), null);
        try {
            return etudiantMapper.toDto(etudiantRepos.save(etudiantMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public EtudiantDto update(EtudiantDto dto) {
        log.info("update etudiant");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        // verification des doubles
        verifUniqueEtudiant (dto.codeEtudiant(), dto.matriculeEtudiant(), dto.id());
        try{
            Optional<Etudiant> etudiant = etudiantRepos.findById(dto.id());
            if (etudiant.isPresent()){
                return etudiantMapper.toDto(etudiantRepos.save(etudiantMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
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
    private void verifUniqueEtudiant(String code, String matricule, Long id) {
        if (id == null) {
            // Mode AJOUT
            if (etudiantRepos.existsBycodeEtudiant(code)) {
                throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
            }
            if (etudiantRepos.existsByMatriculeEtudiant(matricule)) {
                throw new BadRequestException(ErrorCode.CONFLICT, "Ce matricule existe déjà");
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

