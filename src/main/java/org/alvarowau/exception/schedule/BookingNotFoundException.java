package org.alvarowau.exception.schedule;

public class BookingNotFoundException extends ScheduleException {
    public BookingNotFoundException(String message) {
        super(message, "BOOKING_NOT_FOUND");
    }
}
