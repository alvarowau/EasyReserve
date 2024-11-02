package org.alvarowau.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentFacade {

    private ServiceOfferingService serviceOfferingService;



    // Método para crear un nuevo Service Offering
    public ServiceOfferingResponse createServiceOffering(@Valid ServiceOfferingRequest request) {
        return serviceOfferingService.createServiceOffering(request);
    }

//    // Método para crear un nuevo Service Schedule
//    public ServiceSchedule createServiceSchedule(@Valid ServiceScheduleRequest request) {
//        return null;
//    }
//
//    // Método para crear un nuevo Time Slot
//    public TimeSlot createTimeSlot(Long scheduleId, LocalTime startTime, LocalTime endTime) {
//        return null;
//    }
//
//    // Método para crear una nueva cita
//    public Appointment createAppointment(@Valid AppointmentRequest request) {
//        return null;
//    }
//
//    // Puedes agregar más métodos según sea necesario para obtener, actualizar y eliminar servicios y citas
//    public List<Appointment> getAllAppointments() {
//        return null;
//    }
//
//    public Appointment getAppointmentById(Long id) {
//        return null;
//    }
//
//    public void cancelAppointment(Long id) {
//    }
}