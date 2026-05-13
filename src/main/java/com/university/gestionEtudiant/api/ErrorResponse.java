package com.university.gestionEtudiant.api;

public record ErrorResponse<T>(String code, String message, T details) {}