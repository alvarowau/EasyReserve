package org.alvarowau.exception.user;

import org.alvarowau.exception.schedule.ServiceOfferingException;

public class UserProviderNotFoundException extends ServiceOfferingException {
    public UserProviderNotFoundException(String message) {
        super(message);
    }
}