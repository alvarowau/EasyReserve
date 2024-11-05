package org.alvarowau.exception.user;

public class UserProviderNotFoundException extends UserException {
    public UserProviderNotFoundException(String message) {
        super(message, "USER_PROVIDER_NOT_FOUND");
    }
}
