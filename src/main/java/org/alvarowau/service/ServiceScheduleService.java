package org.alvarowau.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.exception.horarios.EmptySlotListException;
import org.alvarowau.exception.horarios.ServiceOfferingNotFoundException;
import org.alvarowau.exception.horarios.TimeSlotMappingException;
import org.alvarowau.model.dto.mapper.MapperServiceSchedule;
import org.alvarowau.model.dto.mapper.MapperTimeSlot;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.alvarowau.repository.ServiceScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceScheduleService {

    private final MapperTimeSlot mapperTimeSlot;
    private final MapperServiceSchedule mapperServiceSchedule;
    private final ServiceOfferingService serviceOfferingService;
    private final HorarioTreatment horarioTreatment;
    private final ServiceScheduleRepository serviceScheduleRepository;

    public ServiceScheduleResponse createServiceSchedule(ServiceScheduleRequest request) {
        log.info("Iniciando creación de ServiceSchedule con request: {}", request);

        // Verifica que la lista de slots no esté vacía
        if (request.slotRequestList() == null || request.slotRequestList().isEmpty()) {
            log.error("La lista de slotRequestList está vacía.");
            throw new EmptySlotListException("La lista de slots no puede estar vacía.");
        }


        Optional<ServiceOffering> optionalServiceOffering = serviceOfferingService
                .getServiceOfferingByUsername(request.nameServiceOffering());

        ServiceOffering offering = optionalServiceOffering.orElseThrow(
                () -> new ServiceOfferingNotFoundException("Service offering no encontrado para el nombre: " + request.nameServiceOffering())
        );


        ServiceSchedule serviceSchedule = mapperServiceSchedule.toEntity(request, offering);

        try {

            List<TimeSlot> timeSlots = request.slotRequestList().stream()
                    .map(slot -> {
                        try {
                            return mapperTimeSlot.toEntity(slot);
                        } catch (Exception e) {
                            log.error("Error al mapear TimeSlot: {}", e.getMessage());
                            throw new TimeSlotMappingException("Error al mapear el TimeSlot: " + slot);
                        }
                    })
                    .toList();

            serviceSchedule.setTimeSlots(timeSlots);
            serviceSchedule.setAppointments(horarioTreatment.generateAvailableAppointments(serviceSchedule));
            return mapperServiceSchedule.toResponse(serviceSchedule);

            //return mapperServiceSchedule.toResponse(serviceScheduleRepository.save(serviceSchedule));

        } catch (Exception e) {
            log.error("Error durante la creación del ServiceSchedule: {}", e.getMessage());
            throw new RuntimeException("Error al crear el ServiceSchedule: " + e.getMessage(), e);
        }
    }

}
