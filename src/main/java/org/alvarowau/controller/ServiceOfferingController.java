package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "Gestión de servicios", description = "Controlador para crear y obtener servicios de los proveedores")
public class ServiceOfferingController {

    private final AppointmentFacade appointmentFacade;

    @Operation(
            summary = "Crear oferta de servicio",
            description = "Permite a un proveedor crear una nueva oferta de servicio",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para crear una oferta de servicio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServiceOfferingRequest.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Oferta de servicio creada exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceOfferingResponse.class)
            )
    )
    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping
    public ResponseEntity<ServiceOfferingResponse> createServiceOffering(@Valid @RequestBody ServiceOfferingRequest request) {
        ServiceOfferingResponse response = appointmentFacade.createServiceOffering(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener servicios por proveedor",
            description = "Permite obtener las ofertas de servicio de un proveedor específico utilizando su nombre de usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ofertas de servicio obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceOfferingResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/provider/{username}")
    public ResponseEntity<List<ServiceOfferingResponse>> getServiceOfferingsByProviderUsername(@PathVariable String username) {
        List<ServiceOfferingResponse> services = appointmentFacade.searchServiceOfferingsByProviderUsername(username);
        return ResponseEntity.ok(services);
    }
}
