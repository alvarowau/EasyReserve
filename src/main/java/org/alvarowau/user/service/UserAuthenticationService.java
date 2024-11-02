package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse authenticateUser(AuthLoginRequest request) {
        // Ya no se necesita verificar si las contraseñas coinciden en el inicio de sesión
        // AuthenticationUtils.ensurePasswordsMatch(request.password(), request.passwordRepeat());

        Authentication authentication = authenticate(request.username(), request.password());
        String accessToken = jwtTokenProvider.createToken(authentication);

        return new LoginResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationFailedException("Credenciales inválidas. No se pudo iniciar sesión.");
        }

        // Crea el token de autenticación con el nombre de usuario, contraseña y roles
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        // Guarda la autenticación en el contexto de seguridad
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        return authentication;
    }
}
