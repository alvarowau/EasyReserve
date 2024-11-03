package org.alvarowau.model.dto.serviceoffering.timeslot;

import java.time.LocalTime;

public record TimeSlotRequest (LocalTime startTime, LocalTime endTime) {
}
