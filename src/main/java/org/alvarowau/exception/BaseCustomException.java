package org.alvarowau.exception;

import lombok.Getter;

@Getter
public abstract class BaseCustomException extends RuntimeException {
    private final String errorCode;

    protected BaseCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}