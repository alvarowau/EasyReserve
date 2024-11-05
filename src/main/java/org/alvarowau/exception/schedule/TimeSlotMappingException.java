package org.alvarowau.exception.schedule;

public class TimeSlotMappingException extends ScheduleException {
    public TimeSlotMappingException(String message) {
        super(message, "TIME_SLOT_MAPPING_ERROR");
    }
}
