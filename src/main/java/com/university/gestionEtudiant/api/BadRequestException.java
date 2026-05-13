package com.university.gestionEtudiant.api;

import ci.tresorpublic.commons.error.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ErrorCode code;

    public BadRequestException(String message) {
        super(message);
        this.code = ErrorCode.BAD_REQUEST;
    }

    public BadRequestException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
