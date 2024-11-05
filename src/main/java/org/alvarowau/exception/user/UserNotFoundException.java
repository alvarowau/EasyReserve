package org.alvarowau.exception.user;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String username) {
        super("Usuario no encontrado: " + username, "USER_NOT_FOUND");
    }
}