package org.alvarowau.populate;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;
import org.alvarowau.service.ServiceScheduleService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScheduleServicePopulator {

    private final ServiceScheduleService serviceScheduleService;

    public List<ServiceScheduleResponse> createScheduleService() {
        List<String> serviceNames = List.of(
                "Consulta General",
                "Terapia Física",
                "Asesoría Legal",
                "Revisión Médica",
                "Evaluación Psicológica",
                "Consultoría Financiera",
                "Diagnóstico Completo",
                "Revisión Técnica",
                "Consulta Veterinaria",
                "Terapia Ocupacional",
                "Asesoría en IT",
                "Diagnóstico Nutricional",
                "Entrenamiento Personal",
                "Asesoría de Marketing",
                "Reparación Electrónica"
        );

        List<ServiceScheduleRequest> schedules = new ArrayList<>();

        for (String serviceName : serviceNames) {
            Set<DayOfWeek> usedDays = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                DayOfWeek day = getUniqueDayOfWeek(usedDays);
                if (day != null) {
                    List<TimeSlotRequest> timeSlots = generateTimeSlot(day);
                    schedules.add(new ServiceScheduleRequest(serviceName, day, timeSlots));
                }
            }
        }

        List<ServiceScheduleResponse> responses = new ArrayList<>();
        for (ServiceScheduleRequest schedule : schedules) {
            ServiceScheduleResponse response = serviceScheduleService.createServiceSchedule(schedule);
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

