package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.booking.BookingHistoryResponse;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.model.dto.feedback.FeedbackRequest;
import org.alvarowau.model.dto.feedback.FeedbackResponse;
import org.alvarowau.model.dto.feedback.ProviderAverageRating;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponseWithId;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
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

    public ServiceScheduleResponse createServiceSchedule(ServiceScheduleRequest request) {
        return serviceScheduleManagementService.createServiceScheduleForProvider(request);
    }

    public List<ServiceOfferingResponse> searchServiceOfferingsByProviderUsername(String username){
        return serviceOfferingManagementService.getServiceOfferingsByProviderUsername(username);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByProvider(String username){
        return appointmentManagementService.getAvailableAppointmentsByProviderUsername(username);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByServiceOfferingName(String serviceOfferingName){
        return appointmentManagementService.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName);
    }
    public List<AppointmentResponseWithId> getAvailableAppointmentsByServiceOfferingNameWithId(String serviceOfferingName){
        return appointmentManagementService.getAvailableAppointmentsByServiceOfferingNameWithId(serviceOfferingName);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDate(LocalDate date){
        return appointmentManagementService.getAvailableAppointmentsByDate(date);
    }

    public List<AppointmentResponse> getAvailableAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentManagementService.getAvailableAppointmentsByDateRange(startDate,endDate);
    }

    public boolean isAppointmentAvailableByTrackingNumber(String trackingNumber) {
        return appointmentManagementService.isAppointmentAvailableByTrackingNumber(trackingNumber);
    }

    public boolean isAppointmentAvailableById(Long id) {
        return appointmentManagementService.isAppointmentAvailableById(id);
    }

    public BookingResponseCreate createBookingByTrackingNumberAppointment(BookingRequestTrackingNumber bookingRequestTrackingNumber){
        return bookingManagementService.createBookingByTrackingNumberAppointment(bookingRequestTrackingNumber);
    }

    public BookingResponseCreate createBookingByIdAppointment(BookingRequestId bookingRequestId){
        return bookingManagementService.createBookingByIdAppointment(bookingRequestId);
    }

    public BookingCancellationResponse cancelBookingByUser(CancelBookingRequest request) {
        return bookingManagementService.cancelBookingByUser(request);
    }

    public BookingCancellationResponse cancelBookingByStaff(CancelBookingRequest request) {
        return bookingManagementService.cancelBookingByStaff(request);
    }

    public List<BookingHistoryResponse> getBookingHistory() {
        return bookingManagementService.getBookingHistory();
    }

    public List<BookingHistoryResponse> getBookingHistory(LocalDate startDate, LocalDate endDate) {
        return bookingManagementService.getBookingHistory(startDate,endDate);
    }

    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        return feedbackManagementService.submitFeedbackByCustomer(request);
    }

    public List<FeedbackResponse> getAllFeedbacksByUserUsername(){
        return feedbackManagementService.getAllFeedbacksByCustomer();
    }

    public List<FeedbackResponse> getFeedbacksByServiceOfferingName(String serviceOfferingName){
        return feedbackManagementService.getFeedbacksByServiceOfferingName(serviceOfferingName);
    }

    public List<FeedbackResponse> getFeedbacksByProviderUsername(String providerUsername){
        return feedbackManagementService.getFeedbacksByProviderUsername(providerUsername);
    }

    public ProviderAverageRating getAverageRatingByProviderUsername(String providerUsername){
        return feedbackManagementService.getAverageRatingByProviderUsername(providerUsername);
    }

    public List<BookingResponseCreate> getBookingsForStaff() {
        return bookingManagementService.listForStaff();
    }
}