package org.alvarowau.model.dto.serviceoffering.serviceschedule;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponse;

import java.time.DayOfWeek;
import java.util.List;

@Schema(description = "Respuesta de la programación de una oferta de servicio para un día específico.")
public record ServiceScheduleResponse(

        @Schema(description = "Nombre de la oferta de servicio programada.", example = "Basic Service Offering")
        String nameServiceOffering,

        @Schema(description = "Día de la semana para la programación de la oferta de servicio.", example = "MONDAY")
        DayOfWeek day,

        @Schema(description = "Lista de las citas programadas para la oferta de servicio en el día especificado.",
                example = "[{\"trackingNumber\": \"12345\", \"serviceName\": \"Consultation\", \"date\": \"2024-11-10\", \"startTime\": \"2024-11-10T09:00:00\", \"endTime\": \"2024-11-10T10:00:00\"}, " +
                        "{\"trackingNumber\": \"12346\", \"serviceName\": \"Consultation\", \"date\": \"2024-11-10\", \"startTime\": \"2024-11-10T10:30:00\", \"endTime\": \"2024-11-10T11:30:00\"}]")
        List<ServiceAppointmentResponse> serviceAppointmentRespons
) {}
