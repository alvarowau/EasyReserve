package org.alvarowau.error;

import lombok.extern.slf4j.Slf4j;
import org.alvarowau.error.model.ApiError;
import org.alvarowau.exception.BaseCustomException;
import org.alvarowau.exception.common.CustomException;
import org.alvarowau.exception.common.GenericException;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private static ResponseEntity<ApiError> getApiErrorResponseEntity(String message, HttpStatus status, String errorCode, String details) {
        ApiError apiError = new ApiError(status, LocalDateTime.now(), message, errorCode, details);
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ApiError> handleBaseCustomException(BaseCustomException ex) {
        log.error("BaseCustomException: ErrorCode: {}, Message: {}", ex.getErrorCode(), ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, ex.getErrorCode(), "Validation error.");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleCustomException(CustomException ex) {
        log.error("CustomException: ErrorCode: {}, Message: {}", ex.getErrorCode(), ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, "CUSTOM_ERROR", "Custom error.");
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiError> handleGenericException(GenericException ex) {
        log.error("Generic Exception: {}", ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC_ERROR", "Error genérico.");
    }

    @ExceptionHandler({
            PasswordsDoNotMatchException.class,
            InvalidRoleException.class,
            AuthenticationFailedException.class
    })
    public ResponseEntity<ApiError> handleBadRequestExceptions(RuntimeException ex) {
        log.error("Bad Request Exception: {}", ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, "BAD_REQUEST", "La solicitud es incorrecta.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage());
        return getApiErrorResponseEntity("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Ocurrió un error en el servidor.");
    }
}
