package org.alvarowau.model.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.enums.BookingStatus;

@Schema(description = "Respuesta con el historial de reservas, que incluye el número de reserva, la fecha de la cita y el estado de la reserva.")
public record BookingHistoryResponse(

        @Schema(description = "Número de la reserva correspondiente al historial.", example = "BOOKING12345")
        String bookingNumber,

        @Schema(description = "Fecha de la cita asociada a la reserva.", example = "2024-11-08")
        String appointmentDate,

        @Schema(description = "Estado actual de la reserva en el historial.", example = "CONFIRMED")
        BookingStatus status
) {}
