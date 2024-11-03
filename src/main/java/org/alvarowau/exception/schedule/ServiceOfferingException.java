package org.alvarowau.exception.schedule;

public class ServiceOfferingException extends RuntimeException {
    public ServiceOfferingException(String message) {
        super(message);
    }

    public ServiceOfferingException(String message, Throwable cause) {
        super(message, cause);
    }
}