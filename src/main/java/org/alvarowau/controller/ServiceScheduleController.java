package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Gestión de horarios de servicio", description = "Controlador para la creación de horarios de servicio")
public class ServiceScheduleController {

    private final AppointmentFacade appointmentFacade;
    private final MapperTimeSlot timeSlotMapper;
    private final MapperServiceSchedule mapperServiceSchedule;

    @Operation(
            summary = "Crear horario de servicio",
            description = "Permite a un proveedor crear un nuevo horario de servicio",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para crear un horario de servicio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServiceOfferingScheduleRequest.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Horario de servicio creado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ServiceScheduleResponse.class)
            )
    )
    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping
    public ResponseEntity<ServiceScheduleResponse> createServiceSchedule(@Valid @RequestBody ServiceOfferingScheduleRequest request) {
        ServiceScheduleResponse response = appointmentFacade.createServiceSchedule(request);
        return ResponseEntity.ok(response);
    }
}
