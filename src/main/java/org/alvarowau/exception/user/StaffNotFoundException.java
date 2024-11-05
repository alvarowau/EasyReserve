package org.alvarowau.exception.user;

public class StaffNotFoundException extends UserException {
    public StaffNotFoundException(String message) {
        super(message, "STAFF_NOT_FOUND");
    }
}
