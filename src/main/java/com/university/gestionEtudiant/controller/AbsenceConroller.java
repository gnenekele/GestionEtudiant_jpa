package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.AbsenceDto;

import com.university.gestionEtudiant.service.AbsenceService;
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
@RequestMapping("api/Absence")
public class AbsenceConroller  {
    private  final AbsenceService absenceService;
    private static final Logger log = LogUtils.getLogger(AbsenceConroller.class);


    @GetMapping
    public ResponseEntity<?> getAllAbsence(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllAbsence");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(absenceService.getAllAbsence(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllAbsence failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAbsenceById(@PathVariable Long id) {
        log.info("HTTP GET /getAbsenceById/{}", id);
        try {
            return ResponseEntity.ok(absenceService.getAbsenceById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getAbsenceById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveAbsence(@RequestBody AbsenceDto dto) {
        log.info("HTTP POST /saveAbsence body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(absenceService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveAbsence failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAbsence(@RequestBody AbsenceDto dto) {
        log.info("HTTP PUT /updateAbsence body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(absenceService.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateAbsence failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAbsence(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteAbsence/{}", id);
        try {
            absenceService.deleteAbsence(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteAbsence failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

}
