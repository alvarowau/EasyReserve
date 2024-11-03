package org.alvarowau.exception.horarios;

public class EmptySlotListException extends RuntimeException {
    public EmptySlotListException(String message) {
        super(message);
    }
}