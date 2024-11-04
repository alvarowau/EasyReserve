package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.BookingHistoryResponse;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentFacade {

    private final ServiceOfferingService serviceOfferingService;
    private final ServiceScheduleService serviceScheduleService;
    private final BookingService bookingService;
    private final AppointmentService appointmentService;

    // Método para crear un nuevo Service Offering
    public ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest request) {
        return serviceOfferingService.createServiceOffering(request);
    }

    // Método para crear un nuevo Service Schedule
    public ServiceScheduleResponse createServiceSchedule(ServiceScheduleRequest request) {
        return serviceScheduleService.createServiceSchedule(request);
    }

    public List<ServiceOfferingResponse> searchServiceOfferingByUsernameProvider(String username){
        return serviceOfferingService.searchServiceOfferingByUsernameProvider(username);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByProviderUsername(String username){
        return appointmentService.getAvailableAppointmentsByProviderUsername(username);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByServiceOfferingName(String serviceOfferingName){
        return appointmentService.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDate(LocalDate date){
        return appointmentService.getAvailableAppointmentsByDate(date);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentService.getAvailableAppointmentsByDateRange(startDate,endDate);
    }

    public boolean isAppointmentAvailableByTrackingNumber(String trackingNumber) {
        return appointmentService.isAppointmentAvailableByTrackingNumber(trackingNumber);
    }

    public boolean isAppointmentAvailableById(Long id) {
        return appointmentService.isAppointmentAvailableById(id);
    }




    public BookingResponseCreate createBookingByTrackingNumberAppointment(BookingRequestTrackingNumber bookingRequestTrackingNumber){
        return bookingService.createBookingByTrackingNumberAppointment(bookingRequestTrackingNumber);
    }

    public BookingResponseCreate createBookingByIdAppointment(BookingRequestId bookingRequestId){
        return bookingService.createBookingByIdAppointment(bookingRequestId);
    }

    public BookingCancellationResponse cancelBookingByUser(CancelBookingRequest request) {
        return bookingService.cancelBookingByUser(request);
    }

    public BookingCancellationResponse cancelBookingByStaff(CancelBookingRequest request) {
        return bookingService.cancelBookingByStaff(request);
    }

    public List<BookingHistoryResponse> getBookingHistory() {
        return bookingService.getBookingHistory();
    }
}