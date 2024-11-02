package org.alvarowau.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message); // Constructor para mensajes genéricos
    }

    public UserNotFoundException(String username, boolean notFound) {
        super("No se pudo encontrar el usuario con nombre de usuario: " + username);
    }
}
