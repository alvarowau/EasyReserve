package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.AuthResponse;
import org.alvarowau.user.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Endpoint for user login.
     *
     * @param request The login request containing username and password.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        return ResponseEntity.ok(userDetailsService.loginUser(request));
    }
}
