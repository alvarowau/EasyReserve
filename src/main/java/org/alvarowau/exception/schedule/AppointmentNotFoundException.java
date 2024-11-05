package org.alvarowau.exception.schedule;

public class AppointmentNotFoundException extends ScheduleException {
    public AppointmentNotFoundException(String message) {
        super(message, "APPOINTMENT_NOT_FOUND");
    }
}
