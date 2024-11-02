package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.service.UserAuthFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAuthFacade userDetailsService;


    @PostMapping("/log-in")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        return ResponseEntity.ok(userDetailsService.authenticateUser(request));
    }
}
