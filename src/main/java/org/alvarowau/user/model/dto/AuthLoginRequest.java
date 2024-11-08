package org.alvarowau.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicita las credenciales de inicio de sesión del usuario, incluyendo el nombre de usuario y la contraseña, además de la confirmación de la contraseña.")
public record AuthLoginRequest(
        @NotBlank(message = "Username cannot be blank")
        @Schema(description = "El nombre de usuario que el cliente utilizará para iniciar sesión", example = "john_doe")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Schema(description = "La contraseña asociada al nombre de usuario", example = "password123")
        String password,

        @NotBlank(message = "Password repeat cannot be blank")
        @Schema(description = "Confirmación de la contraseña del usuario", example = "password123")
        String passwordRepeat
) {
}
