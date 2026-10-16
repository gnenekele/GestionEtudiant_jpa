package com.university.gestionEtudiant.api;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
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
        Map<String, String> errors = new HashMap<>();

        // 1. Vos lignes d'origine pour les erreurs de champs (@NotBlank, @NotNull)
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error ->
                                errors.put(
                                        error.getField(),
                                        error.getDefaultMessage()));

        // 2. L'AJOUT UNIQUE : Insérer l'erreur globale de la période dans la même Map
        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(
                        error ->
                                errors.put(
                                        "coherencePeriode", // Clé explicite pour le client HTTP
                                        error.getDefaultMessage()));

        ErrorResponse<Map<String, String>> body =
                new ErrorResponse<>(
                        ErrorCode.BAD_REQUEST.name(), MessageEnum.GLOBAL_ALERT.getText(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    /**
     * AJOUT : Intercepte les erreurs d'incompatibilité de type d'annotation (HV000030).
     * Empêche l'affichage du message brut Hibernate et convertit l'erreur 500 en une 400 propre.
     */
    @ExceptionHandler(jakarta.validation.UnexpectedTypeException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleUnexpectedTypeException(
            jakarta.validation.UnexpectedTypeException ex) {

        log.error("Erreur de configuration de validation détectée: ", ex);

        Map<String, String> errors = new HashMap<>();
        // Message propre masquant le détail brut technique
        errors.put("configurationErreur", "Le validateur personnalisé n'est pas configuré pour traiter ce type d'objet.");

        ErrorResponse<Map<String, String>> body =
                new ErrorResponse<>(
                        ErrorCode.BAD_REQUEST.name(),
                        MessageEnum.GLOBAL_ALERT.getText(),
                        errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    /**
     * Intercepte les erreurs de doublons en base de données et renvoie
     * une structure d'erreur identique aux erreurs de validation.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {

        log.error("Violation d'intégrité de données détectée : ", ex);

        Map<String, String> details = new HashMap<>();

        // Vérification de la contrainte unique du parent
        if (ex.getMessage() != null && ex.getMessage().contains("uk3kqj2pmu4a7k0f48m4cp9kjbb")) {
            details.put("parentExistant", "Un parent avec ce nom, prénom et adresse email existe déjà.");
        } else {
            details.put("dbConstraint", "Une contrainte d'intégrité de la base de données a été violée.");
        }

        ErrorResponse<Map<String, String>> body = new ErrorResponse<>(
                ErrorCode.BAD_REQUEST.name(), // Devient "BAD_REQUEST" au lieu de "INTERNAL_SERVER_ERROR"
                "An error has occurred. Please wait and try again.", // Votre message générique
                details // L'objet de détails contenant la clé et la description
        );

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
