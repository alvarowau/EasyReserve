package org.alvarowau.model.dto.serviceoffering;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los detalles de una oferta de servicio.")
public record ServiceOfferingResponse(

        @Schema(description = "Nombre de la oferta de servicio.", example = "Corte de cabello")
        String name,

        @Schema(description = "Duración de la oferta de servicio en minutos.", example = "30")
        int duration,

        @Schema(description = "Nombre de usuario del proveedor asociado a la oferta de servicio.", example = "johndoe123")
        String providerUsername
) {}
