package org.alvarowau.exception.horarios;

public class OverlappingTimeSlotsException extends RuntimeException {
    public OverlappingTimeSlotsException(String message) {
        super(message);
    }
}
