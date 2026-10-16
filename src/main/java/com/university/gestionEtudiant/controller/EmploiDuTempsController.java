package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EmploiDuTempsDto;
import com.university.gestionEtudiant.model.dto.EvaluationDto;
import com.university.gestionEtudiant.service.EmploiDuTempsService;
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
@RequestMapping("api/emploiDuTemps")
public class EmploiDuTempsController {
    private final EmploiDuTempsService service;
    private static final Logger log = LogUtils.getLogger(EmploiDuTempsController.class);


    @GetMapping
    public ResponseEntity<?> getAllEmploiTemps(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllEmploiTemps");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllEmploiTemps(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllEmploiTemps failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmploiTempsById(@PathVariable Long id) {
        log.info("HTTP GET /getEmploiTempsById/{}", id);
        try {
            return ResponseEntity.ok(service.getEmploiTempsById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getEmploiTempsById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveEvaluation(@RequestBody EmploiDuTempsDto dto) {
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
    public ResponseEntity<?> updateEvaluation(@RequestBody EmploiDuTempsDto dto) {
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
    public ResponseEntity<?> deleteEmploiTemps(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteEmploiTemps/{}", id);
        try {
            service.deleteEmploiTemps(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteEmploiTemps failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }

    }
}
