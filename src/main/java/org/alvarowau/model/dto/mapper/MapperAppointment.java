package org.alvarowau.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;
import org.alvarowau.model.entity.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperAppointment {

    private final ModelMapper modelMapper;

    public AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getTrackingNumber(),
                appointment.getServiceSchedule().getServiceOffering().getName(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
    }
}
