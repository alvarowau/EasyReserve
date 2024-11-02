package org.alvarowau.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.UserAuthFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAuthFacade userAuthFacade;


    @PostMapping("/sign-up/{role}")
    public ResponseEntity<LoginResponse> registerUser(@PathVariable String role, @Valid @RequestBody UserRegistrationRequest createUser) {
        LoginResponse response;
        try {
            switch (role.toUpperCase()) {
                case "STAFF":
                    response = userAuthFacade.registerUser(createUser, RoleEnum.STAFF);
                    break;
                case "CUSTOMER":
                    response = userAuthFacade.registerUser(createUser, RoleEnum.CUSTOMER);
                    break;
                case "PROVIDER":
                    response = userAuthFacade.registerUser(createUser, RoleEnum.PROVIDER);
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Rol no válido
            }
        } catch (PasswordsDoNotMatchException | InvalidRoleException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(null, ex.getMessage(), null, false));
        } catch (AuthenticationFailedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, ex.getMessage(), null, false));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, "Error interno del servidor.", null, false));
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
