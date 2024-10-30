package org.alvarowau.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String passwordRepeat,
        @NotBlank String email,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String idDocument,
        @NotBlank String primaryPhone,
        String secondaryPhone, // Puede ser opcional
        String additionalEmail, // Puede ser opcional
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zipCode,
        @NotBlank String country
) {
}
