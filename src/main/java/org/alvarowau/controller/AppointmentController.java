package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponseWithId;
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
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByProvider(username));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/{serviceOfferingName}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsForServiceOffering(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/id/{serviceOfferingName}")
    public ResponseEntity<List<AppointmentResponseWithId>> getAvailableAppointmentsForServiceOfferingAndId(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingNameWithId(serviceOfferingName));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date/{date}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDate(parsedDate));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date-range")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDateRange(startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-tracking-number/{trackingNumber}")
    public ResponseEntity<Boolean> isAppointmentAvailableByTrackingNumber(@PathVariable String trackingNumber) {
        boolean isAvailable = appointmentFacade.isAppointmentAvailableByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(isAvailable);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-id/{id}")
    public ResponseEntity<Boolean> isAppointmentAvailableById(@PathVariable Long id) {
        boolean isAvailable = appointmentFacade.isAppointmentAvailableById(id);
        return ResponseEntity.ok(isAvailable);
    }

}
