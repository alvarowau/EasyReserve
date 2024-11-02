package org.alvarowau.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final AppointmentFacade appointmentFacade;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/create")
    public ResponseEntity<ServiceOfferingResponse> createServiceOffering(@Valid @RequestBody ServiceOfferingRequest request){
        return ResponseEntity.ok(appointmentFacade.createServiceOffering(request));
    }
}
