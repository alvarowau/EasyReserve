package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentFacade {

    private final ServiceOfferingService serviceOfferingService;
    private final ServiceScheduleService serviceScheduleService;


    // Método para crear un nuevo Service Offering
    public ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest request) {
        return serviceOfferingService.createServiceOffering(request);
    }

    // Método para crear un nuevo Service Schedule
    public ServiceScheduleResponse createServiceSchedule(ServiceScheduleRequest request) {
        return serviceScheduleService.createServiceSchedule(request);
    }
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