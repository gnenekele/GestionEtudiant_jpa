package com.university.gestionEtudiant.controller;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.InscriptionDto;
import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.service.InscriptionService;
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
@RequestMapping("api/inscription")
public class InscriptionController {
    private final InscriptionService inscriptionService;
    private static final Logger log = LogUtils.getLogger(InscriptionController.class);


    @GetMapping
    public ResponseEntity<?> getAllInscription(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllInscription");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(inscriptionService.getAllInscription(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllInscription failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInscriptionById(@PathVariable Long id) {
        log.info("HTTP GET /getInscriptionById/{}", id);
        try {
            return ResponseEntity.ok(inscriptionService.getInscriptionById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getInscriptionById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InscriptionDto dto) {
        log.info("HTTP POST /saveInscription body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inscriptionService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /saveInscription failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateInscription(@RequestBody InscriptionDto dto) {
        log.info("HTTP PUT /updateInscription body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inscriptionService.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updateInscription failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long id) {
        log.info("HTTP DELETE /deleteInscription/{}", id);
        try {
            inscriptionService.deleteInscription(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deleteInscription failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}