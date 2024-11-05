package org.alvarowau.exception.schedule;

public class InvalidTimeSlotException extends ScheduleException {
    public InvalidTimeSlotException(String message) {
        super(message, "INVALID_TIME_SLOT");
    }
}
