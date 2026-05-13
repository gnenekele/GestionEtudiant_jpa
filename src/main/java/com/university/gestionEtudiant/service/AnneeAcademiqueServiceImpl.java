package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.mapper.AnneeAcademiqueMapper;
import com.university.gestionEtudiant.model.dto.AnneeDto;
import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import com.university.gestionEtudiant.repository.AnneeAcademiqueRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnneeAcademiqueServiceImpl implements AnneeAcademiqueService {

    private final AnneeAcademiqueRepository anneeRepos;
    private final AnneeAcademiqueMapper anneeMapper;
    private static final Logger log = LogUtils.getLogger(Service.class);


    @Override
    public List<AnneeDto> getAllAnnee(Pageable pageable) {
        log.info("loading annee page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return anneeRepos.findAll(pageable).getContent().stream()
                    .map(anneeMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading annee page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }



    public AnneeDto getAnneeById(Long id) {
        log.info("getAnneeById annee for {}",id);
        try {
            Optional<AnneeAcademique> data = anneeRepos.findById(id);
            if (data.isPresent()) {
                return anneeMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getAnneeById annee  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }



    @Override
    public AnneeDto save(AnneeDto dto) {
        log.info("save annee");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}
            // verification des doubles
        verifUniqueAnnee (dto.annee(),null);
        try {
            return anneeMapper.toDto(anneeRepos.save(anneeMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save annee failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public AnneeDto update(AnneeDto dto) {
        log.info("update annee");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        // verification des doubles
        verifUniqueAnnee (dto.annee(), dto.id());
        try{
            Optional<AnneeAcademique> anneeAcademique = anneeRepos.findById(dto.id());
            if (anneeAcademique.isPresent()){
            return anneeMapper.toDto(anneeRepos.save(anneeMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update annee failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }




    @Override
    public void deleteAnnee(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<AnneeAcademique> data = anneeRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            anneeRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete service failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    // The `verifUniqueAnnee` function allows you to check if the label exists.
    private void verifUniqueAnnee(String annee, Long id) {
        boolean exists;

        if (id == null) {
            // Logic for ADD (Add)
            exists = anneeRepos.existsByAnnee(annee);
        } else {
            // Logique pour la MODIFICATION (Update)
            exists = anneeRepos.existsByAnneeAndIdNot(annee, id);
        }

        if (exists) {
            throw new BadRequestException(ErrorCode.CONFLICT, MessageEnum.DUPLICATE_LABEL.getText());
        }
    }
}