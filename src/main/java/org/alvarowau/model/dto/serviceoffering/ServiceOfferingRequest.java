package org.alvarowau.model.dto.serviceoffering;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Solicitud para crear o modificar una oferta de servicio.")
public record ServiceOfferingRequest(

        @NotBlank(message = "Service name cannot be empty")
        @Schema(description = "Nombre de la oferta de servicio.", example = "Corte de cabello")
        String name,

        @Positive(message = "Duration must be a positive number")
        @Schema(description = "Duración de la oferta de servicio en minutos.", example = "30")
        int duration
) {
}
