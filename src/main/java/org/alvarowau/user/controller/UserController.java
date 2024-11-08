package org.alvarowau.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Gestión de usuarios", description = "Controlador para la creación y autenticación de cuentas de usuario")
public class UserController {

    private final UserAccountAuthFacade userAccountAuthFacade;


    @Operation(
            summary = "Registro de usuario con rol específico",
            description = "Permite registrar un nuevo usuario con el rol indicado",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de registro del usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario registrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos de registro o rol no válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Fallo de autenticación",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            )
    })
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
