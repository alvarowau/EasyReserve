package org.alvarowau.model.dto.serviceoffering.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ServiceAppointmentResponseWithId(Long id,
                                               String trackingNumber,
                                               String serviceName,
                                               LocalDate date,
                                               LocalDateTime startTime,
                                               LocalDateTime endTime)  {
}
