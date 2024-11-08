package org.alvarowau.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonPropertyOrder({"username", "message", "accessToken", "status"})
@Schema(description = "Respuesta del login del usuario, que contiene la información básica del usuario y el estado de la autenticación.")
public record LoginResponse(
        @Schema(description = "El nombre de usuario del usuario que ha iniciado sesión", example = "john_doe")
        String username,

        @Schema(description = "Mensaje informativo sobre el estado del login", example = "Login successful")
        String message,

        @JsonProperty("accessToken")
        @Schema(description = "El token JWT generado para el acceso del usuario", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String jwt,

        @Schema(description = "El estado de la autenticación, 'true' si fue exitosa, 'false' si falló")
        boolean status) {
}
