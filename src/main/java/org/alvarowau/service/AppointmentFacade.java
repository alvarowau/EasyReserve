package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.model.dto.feedback.BookingFeedbackRequest;
import org.alvarowau.model.dto.feedback.ProviderAverageRating;
import org.alvarowau.model.dto.feedback.ServiceOfferingFeedbackResponse;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponseWithId;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceOfferingScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentFacade {

    private final ServiceOfferingManagementService serviceOfferingManagementService;
    private final ServiceScheduleManagementService serviceScheduleManagementService;
    private final BookingManagementService bookingManagementService;
    private final AppointmentManagementService appointmentManagementService;
    private final FeedbackManagementService feedbackManagementService;


    public ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest request) {
        return serviceOfferingManagementService.createServiceOfferingForProvider(request);
    }

    public ServiceScheduleResponse createServiceSchedule(ServiceOfferingScheduleRequest request) {
        return serviceScheduleManagementService.createServiceScheduleForProvider(request);
    }

    public List<ServiceOfferingResponse> searchServiceOfferingsByProviderUsername(String username) {
        return serviceOfferingManagementService.getServiceOfferingsByProviderUsername(username);
    }

    public List<ServiceAppointmentResponse> getAvailableAppointmentsByProvider(String username) {
        return appointmentManagementService.getAvailableAppointmentsByProviderUsername(username);
    }

    public List<ServiceAppointmentResponse> getAvailableAppointmentsByServiceOfferingName(String serviceOfferingName) {
        return appointmentManagementService.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName);
    }

    public List<ServiceAppointmentResponseWithId> getAvailableAppointmentsByServiceOfferingNameWithId(String serviceOfferingName) {
        return appointmentManagementService.getAvailableAppointmentsByServiceOfferingNameWithId(serviceOfferingName);
    }

    public List<ServiceAppointmentResponse> getAvailableAppointmentsByDate(LocalDate date) {
        return appointmentManagementService.getAvailableAppointmentsByDate(date);
    }

    public List<ServiceAppointmentResponse> getAvailableAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentManagementService.getAvailableAppointmentsByDateRange(startDate, endDate);
    }

    public boolean isAppointmentAvailableByTrackingNumber(String trackingNumber) {
        return appointmentManagementService.isAppointmentAvailableByTrackingNumber(trackingNumber);
    }

    public boolean isAppointmentAvailableById(Long id) {
        return appointmentManagementService.isAppointmentAvailableById(id);
    }

    public BookingCreationResponse createBookingByTrackingNumberAppointment(BookingRequestByTrackingNumber bookingRequestByTrackingNumber) {
        return bookingManagementService.createBookingByTrackingNumberAppointment(bookingRequestByTrackingNumber);
    }

    public BookingCreationResponse createBookingByIdAppointment(BookingRequestById bookingRequestById) {
        return bookingManagementService.createBookingByIdAppointment(bookingRequestById);
    }

    public BookingCancellationStatusResponse cancelBookingByUser(BookingCancellationRequest request) {
        return bookingManagementService.cancelBookingByUser(request);
    }

    public BookingCancellationStatusResponse cancelBookingByStaff(BookingCancellationRequest request) {
        return bookingManagementService.cancelBookingByStaff(request);
    }

    public List<BookingHistoryResponse> getBookingHistory() {
        return bookingManagementService.getBookingHistory();
    }

    public List<BookingHistoryResponse> getBookingHistory(LocalDate startDate, LocalDate endDate) {
        return bookingManagementService.getBookingHistory(startDate, endDate);
    }

    public ServiceOfferingFeedbackResponse submitFeedback(BookingFeedbackRequest request) {
        return feedbackManagementService.submitFeedbackByCustomer(request);
    }

    public List<ServiceOfferingFeedbackResponse> getAllFeedbacksByUserUsername() {
        return feedbackManagementService.getAllFeedbacksByCustomer();
    }

    public List<ServiceOfferingFeedbackResponse> getFeedbacksByServiceOfferingName(String serviceOfferingName) {
        return feedbackManagementService.getFeedbacksByServiceOfferingName(serviceOfferingName);
    }

    public List<ServiceOfferingFeedbackResponse> getFeedbacksByProviderUsername(String providerUsername) {
        return feedbackManagementService.getFeedbacksByProviderUsername(providerUsername);
    }

    public ProviderAverageRating getAverageRatingByProviderUsername(String providerUsername) {
        return feedbackManagementService.getAverageRatingByProviderUsername(providerUsername);
    }

    public List<BookingCreationResponse> getBookingsForStaff() {
        return bookingManagementService.listForStaff();
    }
}