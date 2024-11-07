package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.BookingFeedbackRequest;
import org.alvarowau.model.dto.feedback.ServiceOfferingFeedbackResponse;
import org.alvarowau.model.dto.feedback.ProviderAverageRating;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final AppointmentFacade appointmentFacade;

    // Endpoint para que un cliente cree un feedback
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ServiceOfferingFeedbackResponse> createFeedback(@RequestBody BookingFeedbackRequest bookingFeedbackRequest) {
        return ResponseEntity.ok(appointmentFacade.submitFeedback(bookingFeedbackRequest));
    }

    // Obtener todos los feedbacks del usuario autenticado
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllUserFeedbacks() {
        return ResponseEntity.ok(appointmentFacade.getAllFeedbacksByUserUsername());
    }

    // Obtener feedbacks de una oferta de servicio específica (nombre de la oferta como parámetro)
    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/service-offerings/{serviceOfferingName}")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllFeedbacksByServiceOffering(@PathVariable String serviceOfferingName) {
        return ResponseEntity.ok(appointmentFacade.getFeedbacksByServiceOfferingName(serviceOfferingName));
    }

    // Obtener todos los feedbacks de un proveedor específico (nombre de usuario como parámetro)
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/providers/{providerUsername}")
    public ResponseEntity<List<ServiceOfferingFeedbackResponse>> getAllFeedbacksByProvider(@PathVariable String providerUsername) {
        return ResponseEntity.ok(appointmentFacade.getFeedbacksByProviderUsername(providerUsername));
    }

    // Obtener el rating promedio de un proveedor específico
    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/providers/{providerUsername}/average-rating")
    public ResponseEntity<ProviderAverageRating> getProviderAverageRating(@PathVariable String providerUsername) {
        return ResponseEntity.ok(appointmentFacade.getAverageRatingByProviderUsername(providerUsername));
    }
}
