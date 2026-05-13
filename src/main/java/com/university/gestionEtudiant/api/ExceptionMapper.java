package com.university.gestionEtudiant.api;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;

/**
 * Classe utilitaire chargée de récupérer le code d'erreur associé à l'exception transmise en tant que
  * paramètre depuis la couche de service. Cette classe est utilisée dans le contrôleur pour extraire le
  * code d'erreur spécifique de l'exception interceptée pour les réponses d'API standardisées.
 */
public class ExceptionMapper {

    /**
     * Analyse l'exception provenant du service afin d'en extraire le code d'erreur correspondant.
     */
    public static ErrorCode getErrorCode(Exception e) {
        return switch (e) {
            case AppException ex -> ex.getCode();
            case DataNotFoundException ex -> ex.getCode();
            case BadRequestException ex -> ex.getCode();
            default -> ErrorCode.INTERNAL_ERROR;
        };
    }
}