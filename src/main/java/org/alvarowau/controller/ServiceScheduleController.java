package org.alvarowau.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.mapper.MapperServiceSchedule;
import org.alvarowau.model.dto.mapper.MapperTimeSlot;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceOfferingScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ServiceScheduleController {

    private final AppointmentFacade appointmentFacade;
    private final MapperTimeSlot timeSlotMapper;
    private final MapperServiceSchedule mapperServiceSchedule;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping
    public ResponseEntity<ServiceScheduleResponse> createServiceSchedule(@Valid @RequestBody ServiceOfferingScheduleRequest request) {
        ServiceScheduleResponse response = appointmentFacade.createServiceSchedule(request);
        return ResponseEntity.ok(response);
    }
}
