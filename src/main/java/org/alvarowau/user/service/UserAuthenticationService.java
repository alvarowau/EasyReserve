package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Importar Logger y LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // Crear un logger para esta clase
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

    public LoginResponse authenticateUser(AuthLoginRequest request) {
        logger.info("Iniciando autenticación para el usuario: {}", request.username());

        Authentication authentication = authenticate(request.username(), request.password());

        String accessToken = jwtTokenProvider.createToken(authentication);
        logger.info("Usuario autenticado con éxito: {}", request.username());

        return new LoginResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        logger.debug("Cargando detalles de usuario para: {}", username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            logger.warn("Usuario no encontrado: {}", username);
            throw new AuthenticationFailedException("Credenciales inválidas. No se pudo iniciar sesión.");
        }

        logger.debug("Verificando la contraseña para: {}", username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.warn("Contraseña incorrecta para el usuario: {}", username);
            throw new AuthenticationFailedException("Credenciales inválidas. No se pudo iniciar sesión.");
        }

        // Obtener los roles del usuario
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles));
        logger.info("Roles del usuario {}: {}", username,authorities); // Log de los roles asociados

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);

        // Guarda la autenticación en el contexto de seguridad
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        logger.info("Autenticación exitosa para el usuario: {}", username);

        return authentication;
    }
}
