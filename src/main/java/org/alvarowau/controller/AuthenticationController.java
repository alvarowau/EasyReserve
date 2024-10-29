package org.alvarowau.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthCreateUser;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.AuthResponse;
import org.alvarowau.user.model.entity.RoleEnum;
import org.alvarowau.user.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
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

    /**
     * Endpoint for user registration.
     *
     * @param role     The role of the user (ADMIN, CUSTOMER, PROVIDER).
     * @param createUser The user data for registration.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/sign_up/{role}")
    public ResponseEntity<AuthResponse> registerUser(@PathVariable String role, @Valid @RequestBody AuthCreateUser createUser) {
        RoleEnum roleEnum;
        switch (role.toUpperCase()) {
            case "ADMIN":
                roleEnum = RoleEnum.ADMIN;
                break;
            case "CUSTOMER":
                roleEnum = RoleEnum.CUSTOMER;
                break;
            case "PROVIDER":
                roleEnum = RoleEnum.PROVIDER;
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid role
        }
        AuthResponse response = userDetailsService.createUser(createUser, roleEnum);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
