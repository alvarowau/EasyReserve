package org.alvarowau.exception.horarios;

public class ServiceOfferingNotFoundException extends RuntimeException {
    public ServiceOfferingNotFoundException(String message) {
        super(message);
    }
}