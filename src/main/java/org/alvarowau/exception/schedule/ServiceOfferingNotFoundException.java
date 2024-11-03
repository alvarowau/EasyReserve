package org.alvarowau.exception.schedule;

public class ServiceOfferingNotFoundException extends RuntimeException {
    public ServiceOfferingNotFoundException(String message) {
        super(message);
    }
}