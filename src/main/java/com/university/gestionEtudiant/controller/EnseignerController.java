package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EnseignerFormDto;
import com.university.gestionEtudiant.model.dto.EtudiantFormDto;
import com.university.gestionEtudiant.model.entity.Enseigner;
import com.university.gestionEtudiant.service.EnseignerService;
import jakarta.validation.Valid;
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
@RequestMapping("api/enseigner")
public class EnseignerController {
    private final EnseignerService service;
    private static final Logger log = LogUtils.getLogger(AnneeController.class);

    @GetMapping
    public ResponseEntity<?> getAllEnseigner(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllEnseigner");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllEnseigner(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllEnseigner failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnseignerById(@PathVariable Long id) {
        log.info("HTTP GET /getEnseignerById/{}", id);
        try {
            return ResponseEntity.ok(service.getEnseignerById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getEnseignerById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> saveEnseigner(@RequestBody EnseignerFormDto dto) {
        log.info("HTTP POST /saveEnseigner body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveEnseigner failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnseigner(@PathVariable Long id, @Valid @RequestBody EnseignerFormDto dto) {
        log.info("HTTP PUT /updateEnseigner body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateEnseigner failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnseigner(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteEnseigner/{}", id);
        try {service.deleteEnseigner(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteEnseigner failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}
