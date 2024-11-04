package org.alvarowau.exception.schedule;

public class CustomConcurrencyException extends RuntimeException {

    public CustomConcurrencyException(String message) {
        super(message);
    }

    public CustomConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
