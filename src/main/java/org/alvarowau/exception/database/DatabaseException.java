package org.alvarowau.exception.database;

import org.alvarowau.exception.BaseCustomException;

public class DatabaseException extends BaseCustomException {
    public DatabaseException(String message, String errorCode) {
        super(message, errorCode);
    }
}
