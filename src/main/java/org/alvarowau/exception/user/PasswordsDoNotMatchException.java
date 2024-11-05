package org.alvarowau.exception.user;

public class PasswordsDoNotMatchException extends UserException {
    public PasswordsDoNotMatchException(String message) {
        super(message, "PASSWORDS_DO_NOT_MATCH");
    }
}
