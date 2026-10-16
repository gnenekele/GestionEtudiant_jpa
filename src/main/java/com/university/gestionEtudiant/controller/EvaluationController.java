package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.dto.EvaluationDto;
import com.university.gestionEtudiant.service.EvaluationService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/evoluation")
public class EvaluationController {
    private final EvaluationService service;
    private static final Logger log = LogUtils.getLogger(EvaluationController.class);


    @GetMapping
    public ResponseEntity<?> getAllEvaluation(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllEvaluation");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllEvaluation(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllEvaluation failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvaluationById(@PathVariable Long id) {
        log.info("HTTP GET /getEvaluationById/{}", id);
        try {
            return ResponseEntity.ok(service.getEvaluationById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getEvaluationById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveEvaluation(@RequestBody EvaluationDto dto) {
        log.info("HTTP POST /saveEvaluation body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveEvaluation failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateEvaluation(@RequestBody EvaluationDto dto) {
        log.info("HTTP PUT /updateEvaluation body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateEvaluation failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvaluation(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteEvaluation/{}", id);
        try {
            service.deleteEvaluation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteEvaluation failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }

    }



}
