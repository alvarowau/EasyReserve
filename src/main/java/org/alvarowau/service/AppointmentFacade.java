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
import org.alvarowau.model.entity.Feedback;
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
    private final FeedbackService feedbackService;

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
    public List<AppointmentResponseWithId> getAvailableAppointmentsByServiceOfferingNameWithId(String serviceOfferingName){
        return appointmentService.getAvailableAppointmentsByServiceOfferingNameWithId(serviceOfferingName);
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

    public List<BookingHistoryResponse> getBookingHistory(LocalDate startDate, LocalDate endDate) {
        return bookingService.getBookingHistory(startDate,endDate);
    }

    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        return feedbackService.submitFeedback(request);
    }

    public List<FeedbackResponse> getAllFeedbacksByUserUsername(){
        return feedbackService.getAllFeedbacksByUserUsername();
    }

    public List<FeedbackResponse> getFeedbacksByServiceOfferingName(String serviceOfferingName){
        return feedbackService.getFeedbacksByServiceOfferingName(serviceOfferingName);
    }

    public List<FeedbackResponse> getFeedbacksByProviderUsername(String providerUsername){
        return feedbackService.getFeedbacksByProviderUsername(providerUsername);
    }

    public ProviderAverageRating getAverageRatingByProviderUsername(String providerUsername){
        return feedbackService.getAverageRatingByProviderUsername(providerUsername);
    }

    public List<BookingResponseCreate> listForStaff() {
        return bookingService.listForStaff();
    }
}