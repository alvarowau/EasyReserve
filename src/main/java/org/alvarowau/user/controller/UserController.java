package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAccountAuthFacade userAccountAuthFacade;

    @PostMapping("/sign-up/{role}")
    public ResponseEntity<LoginResponse> registerUserWithRole(@PathVariable String role, @Valid @RequestBody UserRegistrationRequest createUser) {
        try {
            RoleEnum roleEnum = RoleEnum.valueOf(role.toUpperCase());
            LoginResponse response = userAccountAuthFacade.registerUserAccount(createUser, roleEnum);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (PasswordsDoNotMatchException | InvalidRoleException ex) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (AuthenticationFailedException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Rol no válido.");
        } catch (Exception ex) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor.");
        }
    }

    private ResponseEntity<LoginResponse> buildErrorResponse(HttpStatus status, String message) {
        LoginResponse errorResponse = new LoginResponse(null, message, null, false);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
