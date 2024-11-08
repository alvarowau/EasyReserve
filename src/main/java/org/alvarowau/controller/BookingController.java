package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Gestión de Feedback", description = "Controlador para gestionar feedbacks de clientes, proveedores y servicios")
public class BookingController {
    private final AppointmentFacade appointmentFacade;
    private final SecurityContextUtil securityContextUtil;

    private void validateAuthenticatedUser(String username) {
        if (!securityContextUtil.getAuthenticatedUsername().equals(username)) {
            throw new InvalidCustomerException("Customer username does not match the authenticated user.");
        }
    }

    @Operation(
            summary = "Crear una reserva por número de seguimiento",
            description = "Permite a un cliente crear una reserva utilizando el número de seguimiento",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la solicitud de reserva por número de seguimiento",
                    content = @Content(schema = @Schema(implementation = BookingRequestByTrackingNumber.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reserva creada exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCreationResponse.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-tracking-number")
    public ResponseEntity<BookingCreationResponse> createBookingByTrackingNumber(@RequestBody BookingRequestByTrackingNumber bookingRequestByTrackingNumber) {
        validateAuthenticatedUser(bookingRequestByTrackingNumber.customerUsername());
        BookingCreationResponse response = appointmentFacade.createBookingByTrackingNumberAppointment(bookingRequestByTrackingNumber);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear una reserva por ID",
            description = "Permite a un cliente crear una reserva utilizando el ID del servicio",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la solicitud de reserva por ID",
                    content = @Content(schema = @Schema(implementation = BookingRequestById.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reserva creada exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCreationResponse.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create/by-id")
    public ResponseEntity<BookingCreationResponse> createBookingById(@RequestBody BookingRequestById bookingRequestById) {
        validateAuthenticatedUser(bookingRequestById.usernameCustomer());
        BookingCreationResponse response = appointmentFacade.createBookingByIdAppointment(bookingRequestById);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cancelar una reserva por el cliente",
            description = "Permite a un cliente cancelar una de sus reservas",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para cancelar una reserva",
                    content = @Content(schema = @Schema(implementation = BookingCancellationRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reserva cancelada exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCancellationStatusResponse.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PatchMapping("/cancel/customer")
    public ResponseEntity<BookingCancellationStatusResponse> cancelBookingByUser(@RequestBody BookingCancellationRequest request) {
        validateAuthenticatedUser(request.customerUsername());
        BookingCancellationStatusResponse bookingCancel = appointmentFacade.cancelBookingByUser(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @Operation(
            summary = "Cancelar una reserva por el personal",
            description = "Permite al personal cancelar una reserva de cualquier cliente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para cancelar una reserva",
                    content = @Content(schema = @Schema(implementation = BookingCancellationRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reserva cancelada exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCancellationStatusResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/cancel/staff")
    public ResponseEntity<BookingCancellationStatusResponse> cancelBookingByStaff(@RequestBody BookingCancellationRequest request) {
        BookingCancellationStatusResponse bookingCancel = appointmentFacade.cancelBookingByStaff(request);
        return ResponseEntity.ok(bookingCancel);
    }

    @Operation(
            summary = "Obtener todas las reservas del personal",
            description = "Permite al personal ver todas las reservas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de reservas del personal",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingCreationResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/bookings/staff")
    public ResponseEntity<List<BookingCreationResponse>> getBookingsForStaff() {
        List<BookingCreationResponse> bookings = appointmentFacade.getBookingsForStaff();
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/history")
    @Operation(
            summary = "Obtener historial de reservas del cliente",
            description = "Devuelve el historial de reservas realizadas por el cliente autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Historial de reservas del cliente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingHistoryResponse.class))
                    )
            }
    )
    public ResponseEntity<List<BookingHistoryResponse>> getBookingHistory() {
        List<BookingHistoryResponse> history = appointmentFacade.getBookingHistory();
        return ResponseEntity.ok(history);
    }

    @PreAuthorize("hasAnyRole('PROVIDER')")
    @GetMapping("/provider/reservations")
    @Operation(
            summary = "Obtener historial de reservas de un proveedor",
            description = "Devuelve el historial de reservas de un proveedor en un rango de fechas especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Historial de reservas del proveedor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingHistoryResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error: la fecha de inicio es posterior a la fecha de finalización"
                    )
            }
    )
    public ResponseEntity<List<BookingHistoryResponse>> getProviderBookingHistory(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }
        List<BookingHistoryResponse> history = appointmentFacade.getBookingHistory(startDate, endDate);
        return ResponseEntity.ok(history);
    }
}
