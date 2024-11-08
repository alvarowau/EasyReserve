package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponseWithId;
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
@Tag(name = "Gestión de Citas", description = "Controlador para gestionar citas disponibles")
public class AppointmentController {

    private final AppointmentFacade appointmentFacade;

    @Operation(
            summary = "Obtener citas disponibles por proveedor",
            description = "Permite obtener las citas disponibles para un proveedor específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Citas disponibles obtenidas exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceAppointmentResponse.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-provider/{username}")
    public ResponseEntity<List<ServiceAppointmentResponse>> getAvailableAppointmentsForProvider(@PathVariable String username) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByProvider(username));
    }

    @Operation(
            summary = "Obtener citas disponibles por oferta de servicio",
            description = "Permite obtener las citas disponibles para una oferta de servicio específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Citas disponibles obtenidas exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceAppointmentResponse.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/{serviceOfferingName}")
    public ResponseEntity<List<ServiceAppointmentResponse>> getAvailableAppointmentsForServiceOffering(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingName(serviceOfferingName));
    }

    @Operation(
            summary = "Obtener citas disponibles por oferta de servicio y ID",
            description = "Permite obtener las citas disponibles para una oferta de servicio específica y mostrar el ID de cada cita",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Citas disponibles con ID obtenidas exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceAppointmentResponseWithId.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-service-offering/id/{serviceOfferingName}")
    public ResponseEntity<List<ServiceAppointmentResponseWithId>> getAvailableAppointmentsForServiceOfferingAndId(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByServiceOfferingNameWithId(serviceOfferingName));
    }

    @Operation(
            summary = "Obtener citas disponibles por fecha",
            description = "Permite obtener las citas disponibles para una fecha específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Citas disponibles obtenidas exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceAppointmentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al parsear la fecha"
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date/{date}")
    public ResponseEntity<List<ServiceAppointmentResponse>> getAvailableAppointmentsByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDate(parsedDate));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(
            summary = "Obtener citas disponibles por rango de fechas",
            description = "Permite obtener las citas disponibles en un rango de fechas especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Citas disponibles obtenidas exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceAppointmentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error: la fecha de inicio es posterior a la fecha de finalización"
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-date-range")
    public ResponseEntity<List<ServiceAppointmentResponse>> getAvailableAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(appointmentFacade.getAvailableAppointmentsByDateRange(startDate, endDate));
    }

    @Operation(
            summary = "Comprobar disponibilidad de una cita por número de seguimiento",
            description = "Permite verificar si una cita está disponible mediante su número de seguimiento",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado de disponibilidad de la cita",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-tracking-number/{trackingNumber}")
    public ResponseEntity<Boolean> isAppointmentAvailableByTrackingNumber(@PathVariable String trackingNumber) {
        boolean isAvailable = appointmentFacade.isAppointmentAvailableByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(isAvailable);
    }

    @Operation(
            summary = "Comprobar disponibilidad de una cita por ID",
            description = "Permite verificar si una cita está disponible mediante su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado de disponibilidad de la cita",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/available/by-id/{id}")
    public ResponseEntity<Boolean> isAppointmentAvailableById(@PathVariable Long id) {
        boolean isAvailable = appointmentFacade.isAppointmentAvailableById(id);
        return ResponseEntity.ok(isAvailable);
    }

}
