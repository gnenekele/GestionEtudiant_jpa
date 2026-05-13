package com.university.gestionEtudiant.api;

import ci.tresorpublic.commons.error.ErrorCode;
import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    private final ErrorCode code;

    public DataNotFoundException(String message) {
        super(message);
        this.code = ErrorCode.NOT_FOUND;
    }
}