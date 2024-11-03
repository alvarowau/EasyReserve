package org.alvarowau.error;

import lombok.extern.slf4j.Slf4j;
import org.alvarowau.error.model.ApiError;
import org.alvarowau.exception.horarios.ServiceOfferingNotFoundException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.exception.user.UserProviderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PasswordsDoNotMatchException.class, InvalidRoleException.class,
            AuthenticationFailedException.class, ResponseStatusException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadRequestExceptions(RuntimeException ex) {
        log.error("Bad Request Exception: {}", ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserProviderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleUserProviderNotFoundException(UserProviderNotFoundException ex) {
        log.error("Provider Not Found: {}", ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceOfferingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleServiceOfferingNotFoundException(ServiceOfferingNotFoundException ex) {
        log.error("Service Offering Not Found: {}", ex.getMessage());
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        log.error("Internal Server Error: {}", ex.getMessage());
        return getApiErrorResponseEntity("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        log.error("Authorization Denied: {}", ex.getMessage());
        return getApiErrorResponseEntity("Acceso denegado: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    private static ResponseEntity<ApiError> getApiErrorResponseEntity(String message, HttpStatus status) {
        ApiError apiError = new ApiError(status, LocalDateTime.now(), message);
        return ResponseEntity.status(status).body(apiError);
    }
}
