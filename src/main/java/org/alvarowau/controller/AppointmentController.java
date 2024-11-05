package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentFacade appointmentFacade;

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-provider/{username}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsForProvider(@PathVariable String username) {
        log.info("Fetching available appointments for provider: {}", username);
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByProviderUsername(username));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/{serviceOfferingName}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsForServiceOffering(@PathVariable String serviceOfferingName) {
        log.info("Fetching available appointments for service offering: {}", serviceOfferingName);
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date/{date}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            log.info("Fetching available appointments for date: {}", parsedDate);
            return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDate(parsedDate));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", date);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date-range")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            log.error("Start date {} is after end date {}", startDate, endDate);
            return ResponseEntity.badRequest().body(null);
        }

        log.info("Fetching available appointments from {} to {}", startDate, endDate);
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDateRange(startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-tracking-number/{trackingNumber}")
    public ResponseEntity<Boolean> isAppointmentAvailableByTrackingNumber(@PathVariable String trackingNumber) {
        log.info("Checking availability for tracking number: {}", trackingNumber);
        boolean isAvailable = appointmentFacade.isAppointmentAvailableByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(isAvailable);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-id/{id}")
    public ResponseEntity<Boolean> isAppointmentAvailableById(@PathVariable Long id) {
        log.info("Checking availability for appointment ID: {}", id);
        boolean isAvailable = appointmentFacade.isAppointmentAvailableById(id);
        return ResponseEntity.ok(isAvailable);
    }
}
