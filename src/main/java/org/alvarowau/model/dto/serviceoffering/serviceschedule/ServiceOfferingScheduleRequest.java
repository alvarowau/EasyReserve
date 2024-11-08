package org.alvarowau.model.dto.serviceoffering.serviceschedule;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;

import java.time.DayOfWeek;
import java.util.List;

@Schema(description = "Solicitud para programar los horarios de una oferta de servicio para un día específico.")
public record ServiceOfferingScheduleRequest(

        @Schema(description = "Nombre de la oferta de servicio que se desea programar.", example = "Basic Service Offering")
        String nameServiceOffering,

        @Schema(description = "Día de la semana en el que se desea realizar la programación.", example = "MONDAY")
        DayOfWeek day,

        @Schema(description = "Lista de intervalos de tiempo para la oferta de servicio en el día especificado.",
                example = "[{\"startTime\": \"2024-11-10T09:00:00\", \"endTime\": \"2024-11-10T10:00:00\"}, " +
                        "{\"startTime\": \"2024-11-10T10:30:00\", \"endTime\": \"2024-11-10T11:30:00\"}]")
        List<TimeSlotRequest> slotRequestList
) {
}
