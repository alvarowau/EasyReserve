package org.alvarowau.model.dto.serviceoffering.timeslot;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "Solicitud de franja horaria para una oferta de servicio.")
public record TimeSlotRequest(

        @Schema(description = "Hora de inicio de la franja horaria.", example = "09:00:00")
        LocalTime startTime,

        @Schema(description = "Hora de finalización de la franja horaria.", example = "10:00:00")
        LocalTime endTime
) {
}
