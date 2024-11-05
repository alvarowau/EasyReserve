package org.alvarowau.exception.user;

import org.alvarowau.exception.BaseCustomException;

public class UserException extends BaseCustomException {
    public UserException(String message, String errorCode) {
        super(message, errorCode);
    }
}