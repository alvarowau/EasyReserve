package org.alvarowau.exception.schedule;

public class OverlappingTimeSlotsException extends ScheduleException {
    public OverlappingTimeSlotsException(String message) {
        super(message, "OVERLAPPING_TIME_SLOTS");
    }
}
