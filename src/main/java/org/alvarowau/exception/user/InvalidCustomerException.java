package org.alvarowau.exception.user;

public class InvalidCustomerException extends UserException {
    public InvalidCustomerException(String message) {
        super(message, "INVALID_CUSTOMER");
    }
}
