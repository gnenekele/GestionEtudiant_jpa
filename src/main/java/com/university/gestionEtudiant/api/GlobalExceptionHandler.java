package com.university.gestionEtudiant.api;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
 Centraliser la gestion des exceptions, la liaison de données et l'amélioration du modèle.
 Intercepter les exceptions levées dans les contrôleurs REST et renvoyer une réponse JSON propre.
 Cela évite de réécrire le même bloc try/catch ou annotation @ExceptionHandler dans chaque contrôleur.

 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogUtils.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse<String>> handleAppException(AppException ex) {
        HttpStatus status = mapStatus(ex.getCode());

        log.error(
                "Handled AppException code={} status={} message={} details={}",
                ex.getCode(),
                status.value(),
                ex.getMessage(),
                ex.getDetails(),
                ex);

        ErrorResponse<String> body =
                new ErrorResponse<>(status.name(), ex.getMessage(), ex.getDetails());

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Intercepte les exceptions de validation des arguments de méthode (par exemple, @Valid sur un DTO). Cette méthode
     * centralise la capture des erreurs @NotBlank, @Min, @Positive, etc.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors =
                new HashMap<>(); // Carte permettant de stocker les erreurs par champ (clé : nom du champ, valeur : message d’erreur)
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error ->
                                errors.put(
                                        error.getField(),
                                        error.getDefaultMessage())); // Association de chaque champ défectueux à son

                // message de validation spécifique défini dans le DTO

        ErrorResponse<Map<String, String>> body =
                new ErrorResponse<>(
                        ErrorCode.BAD_REQUEST.name(), MessageEnum.GLOBAL_ALERT.getText(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private HttpStatus mapStatus(ErrorCode code) {
        return switch (code) {
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
        };
    }
}
