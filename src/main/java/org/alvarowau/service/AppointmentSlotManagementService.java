package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.schedule.OverlappingTimeSlotsException;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentSlotManagementService {

    private final AppointmentDateManagementService appointmentDateManagementService; // Inyección de DateManagementService
    private final TrackingNumberManagementService trackingNumberManagementService;

    public List<Appointment> generateAvailableAppointments(ServiceSchedule serviceSchedule) {
        List<TimeSlot> timeSlots = serviceSchedule.getTimeSlots();

        validateAndCheckForOverlappingTimeSlots(timeSlots);

        LocalDate availableDate = appointmentDateManagementService.findNextAvailableDateForAppointment(serviceSchedule.getDay());
        List<Appointment> appointments = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            LocalDateTime startTime = availableDate.atTime(timeSlot.getStartTime());
            LocalDateTime endTime = availableDate.atTime(timeSlot.getEndTime());
            while (startTime.plusMinutes(serviceSchedule.getServiceOffering().getDuration()).isBefore(endTime) ||
                    startTime.plusMinutes(serviceSchedule.getServiceOffering().getDuration()).isEqual(endTime)) {
                Appointment appointment = new Appointment();
                appointment.setStartTime(startTime);
                appointment.setEndTime(startTime.plusMinutes(serviceSchedule.getServiceOffering().getDuration()));
                appointment.setAvailable(true);
                appointment.setTrackingNumber(trackingNumberManagementService.generateTrackingNumberFromDate(availableDate));
                appointment.setDate(availableDate);
                appointment.setServiceSchedule(serviceSchedule);

                appointments.add(appointment);
                startTime = startTime.plusMinutes(serviceSchedule.getServiceOffering().getDuration());
            }
        }

        return appointments;
    }



    // Método para validar los TimeSlots
    private void validateAndCheckForOverlappingTimeSlots(List<TimeSlot> timeSlots) {
        if (areTimeSlotsOverlapping(timeSlots)) {
            throw new OverlappingTimeSlotsException("Los TimeSlots se solapan entre sí.");
        }
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
    private boolean areTimeSlotsOverlapping(List<TimeSlot> timeSlots) {
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot current = timeSlots.get(i);
            for (int j = i + 1; j < timeSlots.size(); j++) {
                TimeSlot next = timeSlots.get(j);
                if (doTimeSlotsOverlap(current, next)) {
                    return true; // Hay solapamiento
                }
            }
        }
        return false;
    }

    // Método para determinar si dos TimeSlot se solapan
    private boolean doTimeSlotsOverlap(TimeSlot first, TimeSlot second) {
        return first.getStartTime().isBefore(second.getEndTime()) && second.getStartTime().isBefore(first.getEndTime());
    }

}