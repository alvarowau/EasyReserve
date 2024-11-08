package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de reserva basada en el número de seguimiento de la cita y el nombre de usuario del cliente.")
public record BookingRequestByTrackingNumber(

        @Schema(description = "Número de seguimiento único de la cita.", example = "A12345XYZ")
        String appointmentTrackingNumber,

        @Schema(description = "Nombre de usuario del cliente asociado a la cita.", example = "johndoe")
        String customerUsername
) {}
