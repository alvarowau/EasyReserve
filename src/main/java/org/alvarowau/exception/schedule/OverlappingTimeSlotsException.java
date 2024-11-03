package org.alvarowau.exception.schedule;

public class OverlappingTimeSlotsException extends RuntimeException {
    public OverlappingTimeSlotsException(String message) {
        super(message);
    }
}
