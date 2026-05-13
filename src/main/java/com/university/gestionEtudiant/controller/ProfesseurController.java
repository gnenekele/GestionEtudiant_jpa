package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.ProfesseurDto;
import com.university.gestionEtudiant.service.ProfesseurService;
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
@RequestMapping("api/professeur")
public class ProfesseurController {
    private final ProfesseurService service;
    private static final Logger log = LogUtils.getLogger(ProfesseurController.class);



    @GetMapping
    public ResponseEntity<?> getAllProf(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllProf");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(service.getAllProf(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllProf failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }}

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfById(@PathVariable Long id) {
        log.info("HTTP GET /getProfById/{}", id);
        try {
            return ResponseEntity.ok(service.getProfById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getProfById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveProf(@RequestBody ProfesseurDto dto) {
        log.info("HTTP POST /saveProf body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveProf failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> updateProf(@RequestBody ProfesseurDto dto) {
        log.info("HTTP PUT /updateProf body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateProf failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProf(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteProf/{}", id);
        try {service.deleteProf(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteProf failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

}
