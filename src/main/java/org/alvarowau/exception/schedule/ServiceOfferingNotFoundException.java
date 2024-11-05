package org.alvarowau.exception.schedule;

public class ServiceOfferingNotFoundException extends ServiceOfferingException {
    public ServiceOfferingNotFoundException(String message) {
        super(message, "SERVICE_OFFERING_NOT_FOUND");
    }
}
