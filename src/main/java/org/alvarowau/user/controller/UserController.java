package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAuthService userAuthService;

    /**
     * Endpoint for user registration.
     *
     * @param role     The role of the user (ADMIN, CUSTOMER, PROVIDER).
     * @param createUser The user data for registration.
     * @return Response entity containing authentication response.
     */
    @PostMapping("/sign-up/{role}")
    public ResponseEntity<LoginResponse> registerUser(@PathVariable String role, @Valid @RequestBody UserRegistrationRequest createUser) {
        LoginResponse response;
        System.out.println("Entro aqui");
        System.out.println(role.toUpperCase());
        switch (role.toUpperCase()) {
            case "STAFF":
                response = userAuthService.registerUser(createUser, RoleEnum.STAFF);
                break;
            case "CUSTOMER":
                response = userAuthService.registerUser(createUser, RoleEnum.CUSTOMER);
                break;
            case "PROVIDER":
                response = userAuthService.registerUser(createUser, RoleEnum.PROVIDER);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid role
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
