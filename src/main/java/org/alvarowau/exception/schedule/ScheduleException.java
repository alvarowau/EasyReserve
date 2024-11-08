package org.alvarowau.exception.schedule;

import org.alvarowau.exception.BaseCustomException;

public class ScheduleException extends BaseCustomException {
    public ScheduleException(String message, String errorCode) {
        super(message, errorCode);
    }
}
