package org.alvarowau.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleRequest;
import org.alvarowau.model.dto.serviceoffering.serviceschedule.ServiceScheduleResponse;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.model.entity.ServiceSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperServiceSchedule {

    private final ModelMapper modelMapper;
    private final MapperAppointment mapperAppointment;

    public ServiceSchedule toEntity(ServiceScheduleRequest request,ServiceOffering serviceOffering) {
        return ServiceSchedule.builder()
                .day(request.day())
                .serviceOffering(serviceOffering)
                .build();
    }

    public ServiceScheduleResponse toResponse(ServiceSchedule serviceSchedule) {
        return new ServiceScheduleResponse(
                serviceSchedule.getServiceOffering().getName(),
                serviceSchedule.getDay(),
                serviceSchedule.getAppointments().stream().map(mapperAppointment::toResponse).toList()
        );
    }
}
