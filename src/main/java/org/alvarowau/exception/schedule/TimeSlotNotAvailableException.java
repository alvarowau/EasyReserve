package org.alvarowau.exception.schedule;

public class TimeSlotNotAvailableException extends ScheduleException {
    public TimeSlotNotAvailableException(String message) {
        super(message, "TIME_SLOT_NOT_AVAILABLE");
    }
}
