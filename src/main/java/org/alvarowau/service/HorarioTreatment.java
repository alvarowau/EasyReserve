package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.horarios.OverlappingTimeSlotsException;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HorarioTreatment {

    private final DateManagementService dateManagementService; // Inyección de DateManagementService

    public List<Appointment> generateAvailableAppointments(ServiceSchedule serviceSchedule) {
        List<TimeSlot> timeSlots = serviceSchedule.getTimeSlots();

        // Comprobar que los TimeSlots son válidos
        validateTimeSlots(timeSlots);

        // Buscar la próxima fecha disponible
        LocalDate availableDate = dateManagementService.findNextAvailableDate(serviceSchedule.getDay());

        List<Appointment> appointments = new ArrayList<>();

        // Generar citas a partir de los TimeSlots
        for (TimeSlot timeSlot : timeSlots) {
            // Calcular el tiempo de inicio y de fin
            LocalDateTime startTime = availableDate.atTime(timeSlot.getStartTime());
            LocalDateTime endTime = availableDate.atTime(timeSlot.getEndTime());

            // Crear una nueva cita
            Appointment appointment = new Appointment();
            appointment.setStartTime(startTime);
            appointment.setEndTime(endTime);
            appointment.setAvailable(true);
            appointment.setServiceName(serviceSchedule.getServiceOffering().getName()); // Asumiendo que hay un método para obtener el nombre del servicio
            appointment.setTrackingNumber(generateTrackingNumber());
            appointment.setDate(availableDate);

            // Agregar a la lista de citas
            appointments.add(appointment);
        }

        return appointments; // Retornar la lista de citas generadas
    }


    // Método para validar los TimeSlots
    private void validateTimeSlots(List<TimeSlot> timeSlots) {
        // Comprobar que no se solapan
        if (checkForOverlappingTimeSlots(timeSlots)) {
            throw new OverlappingTimeSlotsException("Los TimeSlots se solapan entre sí.");
        }

        // Comprobar que cada TimeSlot tiene sentido
        for (TimeSlot timeSlot : timeSlots) {
            if (!timeSlot.getStartTime().isBefore(timeSlot.getEndTime())) {
                throw new IllegalArgumentException("El TimeSlot no tiene una hora de inicio anterior a la de fin.");
            }

            if (!timeSlot.isAvailable()) {
                throw new IllegalArgumentException("El TimeSlot no está disponible.");
            }
        }
    }

    // Método privado que verifica si los TimeSlot se solapan
    private boolean checkForOverlappingTimeSlots(List<TimeSlot> timeSlots) {
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot current = timeSlots.get(i);
            for (int j = i + 1; j < timeSlots.size(); j++) {
                TimeSlot next = timeSlots.get(j);
                if (timeSlotsOverlap(current, next)) {
                    return true; // Hay solapamiento
                }
            }
        }
        return false;
    }

    // Método para determinar si dos TimeSlot se solapan
    private boolean timeSlotsOverlap(TimeSlot first, TimeSlot second) {
        return first.getStartTime().isBefore(second.getEndTime()) && second.getStartTime().isBefore(first.getEndTime());
    }

    // Método para generar un número de seguimiento único
    private String generateTrackingNumber() {
        return UUID.randomUUID().toString(); // Ejemplo de generación de un número de seguimiento único
    }
}
