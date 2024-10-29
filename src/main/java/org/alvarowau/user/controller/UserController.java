package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.AuthCreateUser;
import org.alvarowau.user.model.dto.AuthResponse;
import org.alvarowau.user.model.entity.RoleEnum;
import org.alvarowau.user.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Endpoint for user registration.
     *
     * @param role     The role of the user (ADMIN, CUSTOMER, PROVIDER).
     * @param createUser The user data for registration.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/sign-up/{role}")
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

    // Otros métodos para actualizar o eliminar usuarios pueden agregarse aquí.
}
