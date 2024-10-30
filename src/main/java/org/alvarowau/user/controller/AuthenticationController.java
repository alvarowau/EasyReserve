package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAuthService userDetailsService;

    /**
     * Endpoint for user login.
     *
     * @param request The login request containing username and password.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/log-in")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        return ResponseEntity.ok(userDetailsService.authenticateUser(request));
    }
}
