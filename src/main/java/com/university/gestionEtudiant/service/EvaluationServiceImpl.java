package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EvaluationDto;
import com.university.gestionEtudiant.model.entity.Evaluation;
import com.university.gestionEtudiant.model.entity.Matiere;
import com.university.gestionEtudiant.model.entity.Periode;
import com.university.gestionEtudiant.model.mapper.EvaluationMapper;
import com.university.gestionEtudiant.repository.EvaluationRepository;
import com.university.gestionEtudiant.repository.MatiereRepository;
import com.university.gestionEtudiant.repository.PeriodeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService{

    private  final PeriodeRepository periodeRepos;
    private  final MatiereRepository  matiereRepos;
    private final EvaluationRepository evaluationRepos;
    private final EvaluationMapper  evaluationMapper;
    private static final Logger log = LogUtils.getLogger(Evaluation.class);

    @Override
    public List<EvaluationDto> getAllEvaluation(Pageable pageable) {
        log.info("loading getAllEvaluation page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return evaluationRepos.findAll(pageable).getContent().stream()
                    .map(evaluationMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllEvaluation page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public EvaluationDto getEvaluationById(Long id) {
        log.info("getEvaluationById  for {}",id);
        try {
            Optional<Evaluation> data = evaluationRepos.findById(id);
            if (data.isPresent()) {
                return evaluationMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEvaluationById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }

    @Override
    public EvaluationDto save(EvaluationDto dto) {
        log.info("save evaluation");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        try{
            // 1. Mapper le DTO → Entité (sans les cles etrangere)
            Evaluation evaluation = evaluationMapper.toEntity(dto);

            // 2. Charger cles etrangere depuis la base
            Matiere  matiere = matiereRepos.findById(dto.idMatiere())
                    .orElseThrow(() -> new DataNotFoundException("Matiere non trouvée"));
            Periode periode = periodeRepos.findById(dto.idPeriode())
                    .orElseThrow(() -> new DataNotFoundException("Periode non trouvée"));

            // 3. Associer la periode,la matiere à l'evaluation
            evaluation.setMatiere(matiere);
            evaluation.setPeriode(periode);

            // 4. Sauvegarder
            return evaluationMapper.toDto(evaluationRepos.save(evaluation));
        } catch (Exception e) {
        log.error("save evaluation failed: ", e);
        throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
    }
    }

    @Override
    public EvaluationDto update(EvaluationDto dto) {
        log.info("update Periode");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_REQUIRED.getText());  // ← Corrigé : ID_REQUIS pour update
        }
        try {
            Optional<Evaluation> existing = evaluationRepos.findById(dto.id());
            if (existing.isPresent()) {
                Evaluation evaluation = evaluationMapper.toEntity(dto);

                // Charger l'année académique aussi pour l'update
                Matiere matiere = matiereRepos.findById(dto.idMatiere())
                        .orElseThrow(() -> new DataNotFoundException("Matiere non trouvée"));
                Periode periode = periodeRepos.findById(dto.idPeriode())
                        .orElseThrow(() -> new DataNotFoundException("Periode non trouvée"));

                // 3. Associer la periode,la matiere à l'evaluation
                evaluation.setMatiere(matiere);
                evaluation.setPeriode(periode);
                return evaluationMapper.toDto(evaluationRepos.save(evaluation));
            }
        } catch (Exception e) {
                log.error("update Periode failed: ", e);
                throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
            }
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

        }

    @Override
    public void deleteEvaluation(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Evaluation> data = evaluationRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            evaluationRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Evaluation failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
