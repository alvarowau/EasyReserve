package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.enums.OperationStatus;

@Schema(description = "Respuesta de la cancelación de una reserva, que incluye detalles del usuario, número de reserva, razón y estado de la operación.")
public record BookingCancellationStatusResponse(

        @Schema(description = "Nombre de usuario del cliente que solicitó la cancelación.", example = "jane_doe")
        String username,

        @Schema(description = "Número de reserva de la que se ha solicitado la cancelación.", example = "12345ABC")
        String bookingNumber,

        @Schema(description = "Razón proporcionada por el cliente para la cancelación de la reserva.", example = "Cambio de planes.")
        String reason,

        @Schema(description = "Estado de la operación de cancelación.", example = "SUCCESS")
        OperationStatus status
) {
}
