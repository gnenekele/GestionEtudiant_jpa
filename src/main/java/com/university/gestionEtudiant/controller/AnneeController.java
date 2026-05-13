package com.university.gestionEtudiant.controller;
import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.ExceptionMapper;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.AnneeDto;
import com.university.gestionEtudiant.model.mapper.AnneeAcademiqueMapper;
import com.university.gestionEtudiant.service.AnneeAcademiqueService;
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
@RequestMapping("api/annee")
@Validated
public class AnneeController {
    private final AnneeAcademiqueService anneeService;
    private static final Logger log = LogUtils.getLogger(AnneeController.class);

    @GetMapping
    public ResponseEntity<?> getAllAnnee(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "20") @Positive int size) {
        log.info("HTTP GET /annee");
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(anneeService.getAllAnnee(pageable));
        } catch (Exception e) {
            log.error("HTTP GET /annee failed: ", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnneeById(@PathVariable Long id) {
        log.info("HTTP GET /services/{}", id);
        try {
            return ResponseEntity.ok(anneeService.getAnneeById(id));
        } catch (Exception e) {
            log.error("HTTP GET /services/{} failed", id, e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveAnnee(@Valid @RequestBody AnneeDto dto) {
        log.info("HTTP POST /annee body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(anneeService.save(dto));
        } catch (Exception e) {
            log.error("HTTP POST /annee failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> updateAnnee(@Valid @RequestBody AnneeDto dto) {
        log.info("HTTP PUT /annee body={}", dto);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(anneeService.update(dto));
        } catch (Exception e) {
            log.error("HTTP PUT /annee failed");
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnee(@PathVariable Long id) {
        log.info("HTTP DELETE /annee/{}", id);
        try {
            anneeService.deleteAnnee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("HTTP DELETE /annee failed", e);
            throw new AppException(
                    ExceptionMapper.getErrorCode(e), MessageEnum.GLOBAL_ALERT.getText(), e.getMessage());
        }
    }
}

