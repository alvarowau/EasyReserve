package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de reserva basada en el ID de la reserva y el nombre de usuario del cliente.")
public record BookingRequestById(

        @Schema(description = "ID único de la reserva que se desea consultar o modificar.", example = "12345")
        Long id,

        @Schema(description = "Nombre de usuario del cliente asociado a la reserva.", example = "johndoe")
        String usernameCustomer
) {}
