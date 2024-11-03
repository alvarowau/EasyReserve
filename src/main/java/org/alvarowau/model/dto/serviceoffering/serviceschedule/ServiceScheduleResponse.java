package org.alvarowau.model.dto.serviceoffering.serviceschedule;

import org.alvarowau.model.dto.serviceoffering.appointment.AppointmentResponse;

import java.time.DayOfWeek;
import java.util.List;

public record ServiceScheduleResponse (String nameServiceOffering,
                                       DayOfWeek day,
                                       List<AppointmentResponse> appointmentResponses) {
}
