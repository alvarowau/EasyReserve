package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.user.InvalidCustomerException;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final AppointmentFacade appointmentFacade;
    private final SecurityContextUtil securityContextUtil;

    private void validateAuthenticatedUser(String username) {
        if (!securityContextUtil.getAuthenticatedUsername().equals(username)) {
            throw new InvalidCustomerException("Customer username does not match the authenticated user.");
        }
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-tracking-number")
    public ResponseEntity<BookingCreationResponse> createBookingByTrackingNumber(@RequestBody BookingRequestByTrackingNumber bookingRequestByTrackingNumber) {
        validateAuthenticatedUser(bookingRequestByTrackingNumber.customerUsername());
        BookingCreationResponse response = appointmentFacade.createBookingByTrackingNumberAppointment(bookingRequestByTrackingNumber);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-id")
    public ResponseEntity<BookingCreationResponse> createBookingById(@RequestBody BookingRequestById bookingRequestById) {
        validateAuthenticatedUser(bookingRequestById.usernameCustomer());
        BookingCreationResponse response = appointmentFacade.createBookingByIdAppointment(bookingRequestById);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PatchMapping("/cancel/customer")
    public ResponseEntity<BookingCancellationStatusResponse> cancelBookingByUser(@RequestBody BookingCancellationRequest request) {
        validateAuthenticatedUser(request.customerUsername());
        BookingCancellationStatusResponse bookingCancel = appointmentFacade.cancelBookingByUser(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/cancel/staff")
    public ResponseEntity<BookingCancellationStatusResponse> cancelBookingByStaff(@RequestBody BookingCancellationRequest request) {
        BookingCancellationStatusResponse bookingCancel = appointmentFacade.cancelBookingByStaff(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/bookings/staff")
    public ResponseEntity<List<BookingCreationResponse>> getBookingsForStaff() {
        List<BookingCreationResponse> bookings = appointmentFacade.getBookingsForStaff();
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/history")
    public ResponseEntity<List<BookingHistoryResponse>> getBookingHistory() {
        log.info("Fetching booking history for user: {}", securityContextUtil.getAuthenticatedUsername());
        List<BookingHistoryResponse> history = appointmentFacade.getBookingHistory();
        return ResponseEntity.ok(history);
    }

    @PreAuthorize("hasAnyRole('PROVIDER')")
    @GetMapping("/provider/reservations")
    public ResponseEntity<List<BookingHistoryResponse>> getProviderBookingHistory(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            log.error("Start date {} is after end date {}", startDate, endDate);
            return ResponseEntity.badRequest().body(null);
        }

        log.info("Fetching booking history for provider between dates: {} - {}", startDate, endDate);
        List<BookingHistoryResponse> history = appointmentFacade.getBookingHistory(startDate, endDate);
        return ResponseEntity.ok(history);
    }
}
