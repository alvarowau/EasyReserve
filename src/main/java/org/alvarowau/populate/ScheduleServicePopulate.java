package org.alvarowau.populate;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceOfferingScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;
import org.alvarowau.service.ServiceScheduleManagementService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScheduleServicePopulate {

    private final ServiceScheduleManagementService serviceScheduleManagementService;

    public List<ServiceScheduleResponse> createScheduleService() {
        List<String> serviceNames = List.of(
                "Consulta-General",
                "Terapia-Fisica",
                "Asesoria-Legal",
                "Revision-Medica",
                "Evaluacion-Psologica",
                "Consultoria-Financiera",
                "Diagnostico-Completo",
                "Revision-Tecnica",
                "Consulta-Veterinaria",
                "Terapia-Ocupacional",
                "Asesoria-en-IT",
                "Diagnostico-Nutricional",
                "Entrenamiento-Personal",
                "Asesoria-de-Marketing",
                "Reparacion-Electronica"
        );

        List<ServiceOfferingScheduleRequest> schedules = new ArrayList<>();

        for (String serviceName : serviceNames) {
            Set<DayOfWeek> usedDays = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                DayOfWeek day = getUniqueDayOfWeek(usedDays);
                if (day != null) {
                    List<TimeSlotRequest> timeSlots = generateTimeSlot(day);
                    schedules.add(new ServiceOfferingScheduleRequest(serviceName, day, timeSlots));
                }
            }
        }

        List<ServiceScheduleResponse> responses = new ArrayList<>();
        for (ServiceOfferingScheduleRequest schedule : schedules) {
            ServiceScheduleResponse response = serviceScheduleManagementService.createServiceScheduleForProvider(schedule);
            responses.add(response);
        }
        return responses;
    }

    private DayOfWeek getUniqueDayOfWeek(Set<DayOfWeek> usedDays) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (!usedDays.contains(day)) {
                usedDays.add(day);
                return day;
            }
        }
        return null; // No hay más días únicos disponibles
    }

    private List<TimeSlotRequest> generateTimeSlot(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(8, 0), LocalTime.of(12, 0)),
                    new TimeSlotRequest(LocalTime.of(15, 0), LocalTime.of(20, 0))
            );
            case TUESDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(7, 0), LocalTime.of(12, 0)),
                    new TimeSlotRequest(LocalTime.of(15, 0), LocalTime.of(19, 0))
            );
            case WEDNESDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(9, 0), LocalTime.of(13, 0)),
                    new TimeSlotRequest(LocalTime.of(14, 0), LocalTime.of(18, 0))
            );
            case THURSDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(10, 0), LocalTime.of(14, 0)),
                    new TimeSlotRequest(LocalTime.of(16, 0), LocalTime.of(20, 0))
            );
            case FRIDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(8, 0), LocalTime.of(12, 0)),
                    new TimeSlotRequest(LocalTime.of(15, 0), LocalTime.of(19, 0))
            );
            case SATURDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(9, 0), LocalTime.of(13, 0)),
                    new TimeSlotRequest(LocalTime.of(14, 0), LocalTime.of(17, 0))
            );
            case SUNDAY -> List.of(
                    new TimeSlotRequest(LocalTime.of(10, 0), LocalTime.of(12, 0)),
                    new TimeSlotRequest(LocalTime.of(15, 0), LocalTime.of(18, 0))
            );
        };
    }

}