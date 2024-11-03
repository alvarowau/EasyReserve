package org.alvarowau.exception.user;

public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
