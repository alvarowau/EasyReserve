package org.alvarowau.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.model.dto.mapper.MapperServiceSchedule;
import org.alvarowau.model.dto.mapper.MapperTimeSlot;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ServiceScheduleController {
    private final AppointmentFacade appointmentFacade;
    private final MapperTimeSlot timeSlotMapper;
    private final MapperServiceSchedule mapperServiceSchedule;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/create")
    public ResponseEntity<?> createServiceSchedule(@RequestBody ServiceScheduleRequest request) {
        return ResponseEntity.ok(appointmentFacade.createServiceSchedule(request));
    }
}
