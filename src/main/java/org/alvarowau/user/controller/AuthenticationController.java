package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAccountAuthFacade userAccountAuthFacade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUserLogin(@Valid @RequestBody AuthLoginRequest request) {
        LoginResponse response = userAccountAuthFacade.authenticateUserLogin(request);
        return ResponseEntity.ok(response);
    }
}
