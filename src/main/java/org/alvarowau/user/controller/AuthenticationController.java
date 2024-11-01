package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.AuthResponse;
import org.alvarowau.user.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Endpoint for user login.
     *
     * @param request The login request containing username and password.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        logger.info("Inicio de sesión para el usuario: {}", request.username());

        try {
            // Intenta iniciar sesión y generar el token JWT
            AuthResponse response = userDetailsService.loginUser(request);
            logger.info("Login exitoso. Token generado: {}", response.jwt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Maneja cualquier error durante el proceso de inicio de sesión
            logger.error("Error durante el inicio de sesión para el usuario: {}", request.username(), e);
            return ResponseEntity.status(401).body(null); // Retorna un estado 401 en caso de error
        }
    }
}
