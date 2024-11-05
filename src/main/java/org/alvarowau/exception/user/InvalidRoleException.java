package org.alvarowau.exception.user;

public class InvalidRoleException extends UserException {
    public InvalidRoleException(String message) {
        super(message, "INVALID_ROLE");
    }
}
