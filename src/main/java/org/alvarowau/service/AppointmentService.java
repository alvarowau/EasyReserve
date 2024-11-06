package org.alvarowau.service;


import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.schedule.AppointmentNotFoundException;
import org.alvarowau.model.dto.mapper.MapperAppointment;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponseWithId;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public boolean isAppointmentAvailableByTrackingNumber(String trackingNumber) {
        return appointmentRepository.existsByTrackingNumberAndIsAvailableTrue(trackingNumber);
    }

    public boolean isAppointmentAvailableById(Long id) {
        return appointmentRepository.existsByIdAndIsAvailableTrue(id);
    }

    public Optional<Appointment> findByTrackingNumber(String trackingNumber){
        return appointmentRepository.findByTrackingNumber(trackingNumber);
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public AppointmentResponse markAppointmentAsUnavailableById(Long id) {
        Appointment appointment = findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        return mapperAppointment.toResponse(setAppointmentUnavailable(appointment));
    }

    public AppointmentResponse markAppointmentAsUnavailableByTrackingNumber(String trackingNumber) {
        Appointment appointment = appointmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with tracking number: " + trackingNumber));
        return mapperAppointment.toResponse(setAppointmentUnavailable(appointment));

    }

    public void markAppointmentAsUnavailable(Appointment appointment) {
        setAppointmentUnavailable(appointment);
    }

    private Appointment setAppointmentUnavailable(Appointment appointment) {
        appointment.setAvailable(false);
        return appointmentRepository.save(appointment);
    }

    public Appointment restoreAppointmentAvailability(Appointment appointment) {
        appointment.setAvailable(true);
        return appointmentRepository.save(appointment);
    }

    public List<AppointmentResponseWithId> getAvailableAppointmentsByServiceOfferingNameWithId(String serviceOfferingName) {
        return appointmentRepository
                .findByServiceSchedule_ServiceOffering_NameAndIsAvailableTrue(serviceOfferingName)
                .stream().map(mapperAppointment::toResponseWithId).toList();
    }
}