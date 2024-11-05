package org.alvarowau.exception.schedule;

public class ServiceScheduleCreationException extends ScheduleException {
    public ServiceScheduleCreationException(String message) {
        super(message, "SERVICE_SCHEDULE_CREATION_ERROR");
    }
}
