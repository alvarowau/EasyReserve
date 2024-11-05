package org.alvarowau.exception.user;

public class AuthenticationFailedException extends UserException {
    public AuthenticationFailedException() {
        super("Autenticación fallida.", "AUTH_FAILED");
    }
}