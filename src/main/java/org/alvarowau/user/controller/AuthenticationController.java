package org.alvarowau.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "autentificación", description = "Controlador para la autentificación de usuarios")
public class AuthenticationController {

    private final UserAccountAuthFacade userAccountAuthFacade;

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión de usuario",
            description = "Autentica un usuario en la plataforma utilizando sus credenciales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales de acceso del usuario (nombre de usuario y contraseña)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthLoginRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa, devuelve los detalles de la sesión del usuario",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales inválidas, autenticación fallida",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    public ResponseEntity<LoginResponse> authenticateUserLogin(@Valid @RequestBody AuthLoginRequest request) {
        LoginResponse response = userAccountAuthFacade.authenticateUserLogin(request);
        return ResponseEntity.ok(response);
    }
}
