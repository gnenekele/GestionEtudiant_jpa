package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.FiliereDto;
import com.university.gestionEtudiant.service.FiliereService;
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
@RequestMapping("api/filiere")
public class FiliereController {
    private final FiliereService service;
    private static final Logger log = LogUtils.getLogger(FiliereController.class);


    @GetMapping
    public ResponseEntity<?> getAllFiliere(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllFiliere");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllFiliere(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllFiliere failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFiliereById(@PathVariable Long id) {
        log.info("HTTP GET /getEtudiantById/{}", id);
        try {
            return ResponseEntity.ok(service.getFiliereById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getEtudiantById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveFiliere(@RequestBody FiliereDto dto) {
        log.info("HTTP POST /saveFiliere body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveFiliere failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> updateFiliere(@RequestBody FiliereDto dto) {
        log.info("HTTP PUT /updateFiliere body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateFiliere failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFiliere(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteFiliere/{}", id);
        try {service.deleteFiliere(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteFiliere failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}
