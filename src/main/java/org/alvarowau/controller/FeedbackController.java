package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.BookingFeedbackRequest;
import org.alvarowau.model.dto.feedback.ProviderAverageRating;
import org.alvarowau.model.dto.feedback.ServiceOfferingFeedbackResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Gestión de Feedback", description = "Controlador para gestionar feedbacks de clientes, proveedores y servicios")
public class FeedbackController {

    private final AppointmentFacade appointmentFacade;

    @Operation(
            summary = "Crear un feedback para una reserva",
            description = "Permite al cliente autenticado crear un feedback para una oferta de servicio reservada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la solicitud de feedback",
                    content = @Content(schema = @Schema(implementation = BookingFeedbackRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Feedback creado exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOfferingFeedbackResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ServiceOfferingFeedbackResponse> createFeedback(@RequestBody BookingFeedbackRequest bookingFeedbackRequest) {
        return ResponseEntity.ok(appointmentFacade.submitFeedback(bookingFeedbackRequest));
    }

    @Operation(
            summary = "Obtener todos los feedbacks del usuario autenticado",
            description = "Devuelve una lista de feedbacks hechos por el usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de feedbacks del usuario",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOfferingFeedbackResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllUserFeedbacks() {
        return ResponseEntity.ok(appointmentFacade.getAllFeedbacksByUserUsername());
    }

    @Operation(
            summary = "Obtener feedbacks por oferta de servicio",
            description = "Devuelve todos los feedbacks asociados a una oferta de servicio específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de feedbacks de la oferta de servicio",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOfferingFeedbackResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/service-offerings/{serviceOfferingName}")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllFeedbacksByServiceOffering(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getFeedbacksByServiceOfferingName(serviceOfferingName));
    }


    @Operation(
            summary = "Obtener todos los feedbacks de un proveedor",
            description = "Permite al personal autenticado ver todos los feedbacks de un proveedor específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de feedbacks del proveedor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOfferingFeedbackResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/providers/{providerUsername}")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllFeedbacksByProvider(@PathVariable String providerUsername) {
        return ResponseEntity.ok(appointmentFacade.getFeedbacksByProviderUsername(providerUsername));
    }

    @Operation(
            summary = "Obtener el rating promedio de un proveedor",
            description = "Devuelve la calificación promedio de un proveedor basado en los feedbacks recibidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Calificación promedio del proveedor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProviderAverageRating.class))
                    )
            }
    )
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/providers/{providerUsername}/average-rating")
    public ResponseEntity<ProviderAverageRating> getProviderAverageRating(@PathVariable String providerUsername) {
        return ResponseEntity.ok(appointmentFacade.getAverageRatingByProviderUsername(providerUsername));
    }
}
