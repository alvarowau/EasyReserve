package org.alvarowau.exception.common;

import org.alvarowau.exception.BaseCustomException;

public class CustomException extends BaseCustomException {
    public CustomException(String message, String errorCode) {
        super(message, errorCode);
    }
}
