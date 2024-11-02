package org.alvarowau.model.dto.serviceoffering;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ServiceOfferingRequest(
        @NotBlank(message = "El nombre del servicio no puede estar vacío") String name,
        @Positive(message = "La duración debe ser un número positivo") int duration
) {
}
