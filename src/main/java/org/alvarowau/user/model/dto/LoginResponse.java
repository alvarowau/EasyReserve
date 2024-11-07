package org.alvarowau.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"username", "message", "accessToken", "status"})
public record LoginResponse(
        String username,
        String message,
        @JsonProperty("accessToken") String jwt,
        boolean status) {
}
