package org.alvarowau.exception;

public abstract class BaseCustomException extends RuntimeException {
  private String errorCode;

  public BaseCustomException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}