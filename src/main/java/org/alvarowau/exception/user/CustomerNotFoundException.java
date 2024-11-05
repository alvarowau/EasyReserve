package org.alvarowau.exception.user;

public class CustomerNotFoundException extends UserException {
  public CustomerNotFoundException(String message) {
    super(message, "CUSTOMER_NOT_FOUND");
  }
}
