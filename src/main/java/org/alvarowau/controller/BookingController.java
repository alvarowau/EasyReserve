package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.booking.BookingRequestId;
import org.alvarowau.model.dto.booking.BookingRequestTrackingNumber;
import org.alvarowau.model.dto.booking.BookingResponseCreate;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}