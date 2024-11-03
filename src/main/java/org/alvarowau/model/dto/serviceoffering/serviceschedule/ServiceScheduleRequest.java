package org.alvarowau.model.dto.serviceoffering.serviceschedule;

import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;

import java.time.DayOfWeek;
import java.util.List;

public record ServiceScheduleRequest(String nameServiceOffering, DayOfWeek day, List<TimeSlotRequest> slotRequestList) {
}
