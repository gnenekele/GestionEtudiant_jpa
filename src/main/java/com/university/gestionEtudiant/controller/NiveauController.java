package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.MatiereDto;
import com.university.gestionEtudiant.model.dto.NiveauDto;
import com.university.gestionEtudiant.model.entity.Niveau;
import com.university.gestionEtudiant.service.NiveauService;
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
@RequestMapping("api/niveau")
public class NiveauController {
    private final NiveauService service;
    private static final Logger log = LogUtils.getLogger(NiveauController.class);



    @GetMapping
    public ResponseEntity<?> getAllNiveau(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllNiveau");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllNiveau(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllNiveau failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }}

    @GetMapping("/{id}")
    public ResponseEntity<?> getNiveauById(@PathVariable Long id) {
        log.info("HTTP GET /getNiveauById/{}", id);
        try {
            return ResponseEntity.ok(service.getNiveauById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getNiveauById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveNiveau(@RequestBody NiveauDto dto) {
        log.info("HTTP POST /saveNiveau body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveNiveau failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> updateNiveau(@RequestBody NiveauDto dto) {
        log.info("HTTP PUT /updateMatiere body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateMatiere failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNiveau(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteNiveau/{}", id);
        try {service.deleteNiveau(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteNiveau failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

}
