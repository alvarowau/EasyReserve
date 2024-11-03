package org.alvarowau.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ServiceOfferingController {

    private final AppointmentFacade appointmentFacade;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/create")
    public ResponseEntity<ServiceOfferingResponse> createServiceOffering(@Valid @RequestBody ServiceOfferingRequest request){
        return ResponseEntity.ok(appointmentFacade.createServiceOffering(request));
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @GetMapping("/search/{username}")
    public ResponseEntity<List<ServiceOfferingResponse>> searchServiceOfferingByUsernameProvider(@PathVariable String username){
        return ResponseEntity.ok(appointmentFacade.searchServiceOfferingByUsernameProvider(username));
    }
}
