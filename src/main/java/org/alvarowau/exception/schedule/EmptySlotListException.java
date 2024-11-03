package org.alvarowau.exception.schedule;

public class EmptySlotListException extends RuntimeException {
    public EmptySlotListException(String message) {
        super(message);
    }
}