package org.alvarowau.exception.schedule;

public class ServiceOfferingException extends RuntimeException {
    private String errorCode;

    public ServiceOfferingException(String message) {
        super(message);
    }

    public ServiceOfferingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceOfferingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
