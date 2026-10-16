package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.dto.ClasseFormDto;
import com.university.gestionEtudiant.service.ClasseService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Classe")
//@Validated
public class ClasseController {
    private  final ClasseService classeService;
    private static final Logger log = LogUtils.getLogger(ClasseController.class);

    @GetMapping
    public ResponseEntity<?> getAllClasse(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllClasse");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(classeService.getAllClasse(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllClasse failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getClasseById(@PathVariable Long id) {
        log.info("HTTP GET /getClasseById/{}", id);
        try {
            return ResponseEntity.ok(classeService.getClasseById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getClasseById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveClasse(@Valid @RequestBody ClasseFormDto dto) {
        log.info("HTTP POST /saveClasse body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(classeService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveClasse failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateClasse(@PathVariable Long id, @Valid @RequestBody ClasseFormDto dto) {
        log.info("HTTP PUT /updateClasse body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(classeService.update(id,dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateClasse failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClasse(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteClasse/{}", id);
        try {
            classeService.deleteClasse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteClasse failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

}
