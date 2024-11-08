package org.alvarowau.exception.schedule;

import lombok.Getter;

@Getter
public class ServiceOfferingException extends RuntimeException {
    private final String errorCode;

    public ServiceOfferingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceOfferingException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }


}
