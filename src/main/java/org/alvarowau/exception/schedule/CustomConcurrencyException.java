package org.alvarowau.exception.schedule;

public class CustomConcurrencyException extends ScheduleException {
    public CustomConcurrencyException(String message) {
        super(message, "CONCURRENCY_ERROR");
    }

    public CustomConcurrencyException(String message, Throwable cause) {
        super(message, "CONCURRENCY_ERROR");
        initCause(cause);
    }
}
