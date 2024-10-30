package org.alvarowau.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Clase que representa la respuesta de autenticación.
 * Contiene el nombre de usuario, un mensaje de estado,
 * el token de acceso y el estado de la autenticación.
 */
@JsonPropertyOrder({"username", "message", "accessToken", "status"})
public record LoginResponse(
        String username,
        String message,
        @JsonProperty("accessToken") String jwt,
        boolean status) {
}
