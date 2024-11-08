package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.enums.BookingStatus;

import java.time.LocalDate;

@Schema(description = "Respuesta de la creación de una reserva, que incluye detalles del número de seguimiento, fecha, rango horario, estado de la reserva y el usuario del cliente.")
public record BookingCreationResponse(

        @Schema(description = "Número de seguimiento único de la reserva.", example = "TRACK123456")
        String trackingNumber,

        @Schema(description = "Fecha de la reserva.", example = "2024-11-08")
        LocalDate date,

        @Schema(description = "Rango de tiempo reservado para la cita.", example = "14:00 - 15:00")
        String timeRange,

        @Schema(description = "Número de reserva asignado para esta reserva.", example = "BOOKING12345")
        String bookingNumber,

        @Schema(description = "Estado actual de la reserva.", example = "CONFIRMED")
        BookingStatus status,

        @Schema(description = "Nombre de usuario del cliente que realizó la reserva.", example = "john_doe")
        String customerUsername
) {
}
