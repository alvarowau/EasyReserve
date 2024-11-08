package org.alvarowau.model.dto.serviceoffering.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Respuesta con la información detallada de una cita de servicio, incluyendo su identificador único.")
public record ServiceAppointmentResponseWithId(

        @Schema(description = "Identificador único de la cita de servicio.", example = "1")
        Long id,

        @Schema(description = "Número de seguimiento único de la cita.", example = "APPT-12345")
        String trackingNumber,

        @Schema(description = "Nombre del servicio asociado a la cita.", example = "Premium Cleaning Service")
        String serviceName,

        @Schema(description = "Fecha en la que se llevará a cabo la cita de servicio.", example = "2024-11-10")
        LocalDate date,

        @Schema(description = "Hora de inicio de la cita.", example = "2024-11-10T10:00:00")
        LocalDateTime startTime,

        @Schema(description = "Hora de finalización de la cita.", example = "2024-11-10T12:00:00")
        LocalDateTime endTime
) {}
