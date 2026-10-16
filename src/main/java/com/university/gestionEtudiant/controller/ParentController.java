package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;

import com.university.gestionEtudiant.model.dto.parentFormDto;
import com.university.gestionEtudiant.service.ParentService;
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
@RequestMapping("api/parent")
public class ParentController {
        private final ParentService parentService;
    private static final Logger log = LogUtils.getLogger(ParentController.class);

    @GetMapping
    public ResponseEntity<?> getAllParent(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllParent");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(parentService.getAllParent(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllParent failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParentById(@PathVariable Long id) {
        log.info("HTTP GET /getNoteById/{}", id);
        try {
            return ResponseEntity.ok(parentService.getParentById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getNoteById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> saveParent(@RequestBody parentFormDto dto) {
        log.info("HTTP POST /saveParent body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(parentService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveParent! failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateParent(
            @PathVariable Long id,
            @Valid @RequestBody parentFormDto dto) {

        log.info("HTTP PUT /{}/updateParent body={}", id, dto);
        try {
            return ResponseEntity.ok(parentService.update(id, dto));
        } catch (AppException e) {
            // ✅ Relance l'exception originale sans la masquer
            log.error("HTTP PUT /{}/updateParent failed: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("HTTP PUT /{}/updateParent unexpected error", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e),
                    MessageEnum.GLOBAL_ALERT.getText(),
                    e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParent(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteParent/{}", id);
        try {
            parentService.deleteParent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteParent failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}
