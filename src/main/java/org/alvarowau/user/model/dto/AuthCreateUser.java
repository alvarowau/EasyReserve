package org.alvarowau.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username,
                             @NotBlank String password,
                             @NotBlank String passwordRepeat) {
}
