package com.university.gestionEtudiant.controller;


import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.AnneeDto;
import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.service.PeriodeService;
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
@RequestMapping("api/periode")
@Validated
public class PeriodeController {
    private final PeriodeService periodeService;
    private static final Logger log = LogUtils.getLogger(PeriodeController.class);

    @GetMapping
    public ResponseEntity<?> getAllPeriode(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /getAllPeriode");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(periodeService.getAllPeriode(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /getAllPeriode failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPeriodeById(@PathVariable Long id) {
        log.info("HTTP GET /getPeriodeById/{}", id);
        try {
            return ResponseEntity.ok(periodeService.getPeriodeById(id));
        } catch (Exception e) {
            log.error("HTTP GET /getPeriodeById/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> savePeriode(@Valid @RequestBody PeriodeDto dto) {
        log.info("HTTP POST /annee body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(periodeService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /annee failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> updatePeriode(@Valid @RequestBody PeriodeDto dto) {
        log.info("HTTP PUT /updatePeriode body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(periodeService.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /updatePeriode failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeriode(@PathVariable Long id) {
        log.info("HTTP DELETE /deletePeriode/{}", id);
        try {
            periodeService.deletePeriode(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /deletePeriode failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}
