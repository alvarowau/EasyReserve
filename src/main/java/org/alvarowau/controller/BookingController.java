package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.BookingHistoryResponse;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final AppointmentFacade appointmentFacade;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-tracking-number")
    public ResponseEntity<BookingResponseCreate> createBookingByTrackingNumber(@RequestBody BookingRequestTrackingNumber bookingRequestTrackingNumber) {
        BookingResponseCreate response = appointmentFacade.createBookingByTrackingNumberAppointment(bookingRequestTrackingNumber);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-id")
    public ResponseEntity<BookingResponseCreate> createBookingById(@RequestBody BookingRequestId bookingRequestId) {
        BookingResponseCreate response = appointmentFacade.createBookingByIdAppointment(bookingRequestId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/cancel/customer")
    public ResponseEntity<BookingCancellationResponse> cancelBookingByUser(@RequestBody CancelBookingRequest request){
        BookingCancellationResponse bookingCancel = appointmentFacade.cancelBookingByUser(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/cancel/staff")
    public ResponseEntity<BookingCancellationResponse> cancelBookingByStaff(@RequestBody CancelBookingRequest request) {
        BookingCancellationResponse bookingCancel = appointmentFacade.cancelBookingByStaff(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/history")
    public ResponseEntity<List<BookingHistoryResponse>> getBookingHistory() {
        List<BookingHistoryResponse> history = appointmentFacade.getBookingHistory();
        return ResponseEntity.ok(history);
    }

}