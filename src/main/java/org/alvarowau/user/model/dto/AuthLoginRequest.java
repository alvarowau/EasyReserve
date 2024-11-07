package org.alvarowau.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotBlank(message = "Password repeat cannot be blank")
        String passwordRepeat
) {
}