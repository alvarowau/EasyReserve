package org.alvarowau.exception.user;

public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(String message) {
        super(message);
    }
}