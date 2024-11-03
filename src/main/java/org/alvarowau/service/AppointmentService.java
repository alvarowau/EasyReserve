package org.alvarowau.service;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.mapper.MapperAppointment;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MapperAppointment mapperAppointment;

    public List<AppointmentResponse> getAvailableAppointmentsByProviderUsername(String username) {
        return appointmentRepository
                .findByServiceSchedule_ServiceOffering_Provider_UsernameAndIsAvailableTrue(username)
                .stream().map(mapperAppointment::toResponse).toList();
    }

    public List<AppointmentResponse> getAvailableAppointmentsByServiceOfferingName(String nameServiceOffering) {
        return appointmentRepository
                .findByServiceSchedule_ServiceOffering_NameAndIsAvailableTrue(nameServiceOffering)
                .stream().map(mapperAppointment::toResponse).toList();
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByIsAvailableTrueAndDate(date)
                .stream().map(mapperAppointment::toResponse).toList();
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByIsAvailableTrueAndDateBetween(startDate,endDate)
                .stream().map(mapperAppointment::toResponse).toList();
    }
}
