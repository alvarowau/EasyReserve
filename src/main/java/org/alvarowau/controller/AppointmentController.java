package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByProviderUsername(username));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/{serviceOfferingName}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsForServiceOffering(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date/{date}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDate(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date); // Convertir el String a LocalDate
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDate(parsedDate));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date-range")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointmentsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDateRange(startDate, endDate));
    }
}
