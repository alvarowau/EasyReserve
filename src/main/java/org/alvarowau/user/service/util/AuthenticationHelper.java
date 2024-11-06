package org.alvarowau.user.service.util;

import org.alvarowau.exception.user.PasswordsDoNotMatchException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class AuthenticationHelper {

    public static void validatePasswordsMatch(String password, String passwordRepeat) {
        if (!password.equals(passwordRepeat)) {
            throw new PasswordsDoNotMatchException("Las contraseñas no coinciden.");
        }
    }

    public static Authentication createAuthenticationToken(String username, String password, String role) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
}
