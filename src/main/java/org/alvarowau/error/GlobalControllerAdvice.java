package org.alvarowau.error;

import org.alvarowau.error.model.ApiError;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.alvarowau.exception.user.AuthenticationFailedException; // Importar la excepción
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    // Manejo de excepciones de rol no válido, contraseñas que no coinciden, etc.
    @ExceptionHandler({PasswordsDoNotMatchException.class, InvalidRoleException.class,
            AuthenticationFailedException.class, ResponseStatusException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadRequestExceptions(RuntimeException ex) {
        return getApiErrorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        return getApiErrorResponseEntity("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepciones de autorización denegada
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        logger.error("Acceso denegado: {}", ex.getMessage());
        return getApiErrorResponseEntity("Acceso denegado: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    private static ResponseEntity<ApiError> getApiErrorResponseEntity(String message, HttpStatus status) {
        logger.error("Error: {}", message);
        ApiError apiError = new ApiError(status, LocalDateTime.now(), message);
        return ResponseEntity.status(status).body(apiError);
    }
}