package org.alvarowau.exception.user;

import org.alvarowau.exception.horarios.ServiceOfferingException;

public class UserProviderNotFoundException extends ServiceOfferingException {
    public UserProviderNotFoundException(String message) {
        super(message);
    }
}