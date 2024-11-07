package org.alvarowau.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponse;
import org.alvarowau.model.dto.serviceoffering.appointment.ServiceAppointmentResponseWithId;
import org.alvarowau.model.entity.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperAppointment {

    private final ModelMapper modelMapper;

    public ServiceAppointmentResponse toResponse(Appointment appointment) {
        return new ServiceAppointmentResponse(
                appointment.getTrackingNumber(),
                appointment.getServiceSchedule().getServiceOffering().getName(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
    }

    public ServiceAppointmentResponseWithId toResponseWithId(Appointment appointment) {
        return new ServiceAppointmentResponseWithId(
                appointment.getId(),
                appointment.getTrackingNumber(),
                appointment.getServiceSchedule().getServiceOffering().getName(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
    }
}
