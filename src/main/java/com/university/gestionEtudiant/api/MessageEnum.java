package com.university.gestionEtudiant.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageEnum {
    GLOBAL_ALERT(
            "An error has occurred. Please wait and try again."), // Generic error message for unhandled
    // exceptions.
    DATA_NOT_FOUND("Data not found"), // Error message for missing database entries.

    ID_NOT_REQUIRED(
            "Id not required"), // The identifier must not be provided (e.g., when creating a new
    // resource).
    ID_REQUIRED("Id required"), // The identifier is mandatory (e.g., for an update or deletion).

    // ✅ Doublons
    DUPLICATE_LABEL("The label already exists");
    // DUPLICATE_RESOURCE("This resource already exists");

    public final String text;
}
