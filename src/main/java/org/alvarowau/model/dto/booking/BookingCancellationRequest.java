package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para cancelar una reserva, incluyendo el número de reserva, el nombre de usuario del cliente y la razón de la cancelación.")
public record BookingCancellationRequest(

        @Schema(description = "Número de la reserva que se desea cancelar.", example = "12345ABC")
        String bookingNumber,

        @Schema(description = "Nombre de usuario del cliente que realiza la cancelación.", example = "jane_doe")
        String customerUsername,

        @Schema(description = "Razón de la cancelación de la reserva.", example = "El cliente ya no puede asistir a la cita.")
        String reason
) {
}