package org.alvarowau.exception.common;

import org.alvarowau.exception.BaseCustomException;

public class GenericException extends BaseCustomException {
    public GenericException(String message, String errorCode) {
        super(message, errorCode);
    }
}
