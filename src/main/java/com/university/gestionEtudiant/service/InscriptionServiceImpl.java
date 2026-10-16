package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EvaluationDto;
import com.university.gestionEtudiant.model.dto.InscriptionDto;
import com.university.gestionEtudiant.model.entity.*;
import com.university.gestionEtudiant.model.mapper.InscriptionMapper;
import com.university.gestionEtudiant.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService{
    private  final EtudiantRepository etudiantRepos;
    private  final AnneeAcademiqueRepository anneeRepos;
    private  final ClasseRepository classeRepos;
    private final InscriptionRepository inscriptionRepos;
    private final InscriptionMapper inscriptionMapper;
    private static final Logger log = LogUtils.getLogger(Inscription.class);


    @Override
    public List<InscriptionDto> getAllInscription(Pageable pageable) {
        log.info("loading getAllInscription page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return inscriptionRepos.findAll(pageable).getContent().stream()
                    .map(inscriptionMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllInscription page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
    @Override
    public InscriptionDto getInscriptionById(Long id) {
        log.info("getInscriptionById  for {}",id);
        try {
            Optional<Inscription> data = inscriptionRepos.findById(id);
            if (data.isPresent()) {
                return inscriptionMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getInscriptionById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public InscriptionDto save(InscriptionDto dto) {
        log.info("save Inscription");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        try {
            // 1. Mapper le DTO → Entité (sans les cles etrangere)
            Inscription inscription = inscriptionMapper.toEntity(dto);

            // 2. Charger cles etrangere depuis la base
            Etudiant etudiant = etudiantRepos.findById(dto.idEtudiant())
                    .orElseThrow(() -> new DataNotFoundException("Etudiant n'existe pas"));
            AnneeAcademique annee = anneeRepos.findById(dto.idAnnee())
                    .orElseThrow(() -> new DataNotFoundException("AnneeAcademique n'existe pas"));
            Classe classe = classeRepos.findById(dto.idClasse())
                    .orElseThrow(() -> new DataNotFoundException("Classe n'existe pas"));


            // 3. Associer la Classe,la AnneeAcademique,Etudiant à l' inscription
            inscription.setIdEtudiant(etudiant);
            inscription.setIdAnnee(annee);
            inscription.setIdClasse(classe);

            // 4. Sauvegarder
            return inscriptionMapper.toDto(inscriptionRepos.save(inscription));
        } catch (Exception e) {
            log.error("save evaluation failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public InscriptionDto update(InscriptionDto dto) {
        return null;
    }

    @Override
    public void deleteInscription(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Inscription> data = inscriptionRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            inscriptionRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Evaluation failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

}
