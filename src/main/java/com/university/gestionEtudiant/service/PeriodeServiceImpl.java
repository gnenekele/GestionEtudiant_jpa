package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.PeriodeDto;

import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import com.university.gestionEtudiant.model.entity.Periode;
import com.university.gestionEtudiant.model.mapper.PeriodeMapper;
import com.university.gestionEtudiant.repository.AnneeAcademiqueRepository;
import com.university.gestionEtudiant.repository.PeriodeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PeriodeServiceImpl implements PeriodeService{
    public final PeriodeRepository periodeRepos;
    public final PeriodeMapper periodeMapper;
    public final AnneeAcademiqueRepository anneeRepos;
    private static final Logger log = LogUtils.getLogger(Periode.class);

    @Override
    public List<PeriodeDto> getAllPeriode(Pageable pageable) {
        log.info("loading getAllPeriode page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return periodeRepos.findAll(pageable).getContent().stream()
                    .map(periodeMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllPeriode page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }

    @Override
    public PeriodeDto getPeriodeById(Long id) {
        log.info("getPeriodeById  for {}",id);
        try {
            Optional<Periode> data = periodeRepos.findById(id);
            if (data.isPresent()) {
                return periodeMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getPeriodeById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }

    @Override
    public PeriodeDto save(PeriodeDto dto) {
        log.info("save Periode");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }

        try {
            // 1. Mapper le DTO → Entité (sans l'année)
            Periode periode = periodeMapper.toEntity(dto);

            // 2. Charger l'AnneeAcademique depuis la base
            AnneeAcademique annee = anneeRepos.findById(dto.idAnnee())
                    .orElseThrow(() -> new DataNotFoundException("Année académique non trouvée"));

            // 3. Associer l'année à la période
            periode.setAnneeAcademique(annee);

            // 4. Sauvegarder
            return periodeMapper.toDto(periodeRepos.save(periode));

        } catch (Exception e) {
            log.error("save Periode failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public PeriodeDto update(PeriodeDto dto) {
        log.info("update Periode");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_REQUIRED.getText());  // ← Corrigé : ID_REQUIS pour update
        }

        try {
            Optional<Periode> existing = periodeRepos.findById(dto.id());
            if (existing.isPresent()) {
                Periode periode = periodeMapper.toEntity(dto);

                // Charger l'année académique aussi pour l'update
                AnneeAcademique annee = anneeRepos.findById(dto.idAnnee())
                        .orElseThrow(() -> new DataNotFoundException("Année académique non trouvée"));
                periode.setAnneeAcademique(annee);

                return periodeMapper.toDto(periodeRepos.save(periode));
            }
        } catch (Exception e) {
            log.error("update Periode failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public void deletePeriode(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Periode> data = periodeRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            periodeRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
