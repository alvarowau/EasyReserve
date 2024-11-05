package org.alvarowau.exception.schedule;

public class EmptySlotListException extends ScheduleException {
    public EmptySlotListException(String message) {
        super(message, "EMPTY_SLOT_LIST");
    }
}
