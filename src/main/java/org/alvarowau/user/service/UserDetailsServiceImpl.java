package org.alvarowau.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.AuthCreateUser;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.AuthResponse;
import org.alvarowau.user.model.entity.RoleEnum;
import org.alvarowau.user.model.entity.UserEntity;
import org.alvarowau.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j // Agrega soporte para logging con Lombok
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenProvider utils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Cargando usuario por nombre de usuario: {}", username);
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado: {}", username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        log.debug("Usuario encontrado: {} con rol: {}", username, user.getRole().name());

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorities
        );
    }

    public AuthResponse loginUser(AuthLoginRequest request) {
        log.info("Intentando autenticar usuario: {}", request.username());
        validatePasswordsMatch(request.password(), request.passwordRepeat());

        try {
            Authentication authentication = authenticate(request.username(), request.password());
            String accessToken = utils.createToken(authentication);
            log.info("Autenticación exitosa para el usuario: {}", request.username());
            return new AuthResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
        } catch (BadCredentialsException e) {
            log.error("Error de autenticación para el usuario: {}", request.username(), e);
            throw e;
        }
    }

    public AuthResponse createUser(@Valid AuthCreateUser request, RoleEnum rol) {
        log.info("Creando nuevo usuario con nombre: {}", request.username());
        validatePasswordsMatch(request.password(), request.passwordRepeat());

        String username = request.username();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        userEntity.setRole(rol);
        userEntity.setEnabled(true);
        userEntity.setAccountNoExpired(true);
        userEntity.setCredentialNoExpired(true);
        userEntity.setAccountNoLocked(true);

        UserEntity entity = userRepository.save(userEntity);
        log.info("Usuario {} creado exitosamente con rol {}", username, rol);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + entity.getRole().name())
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                entity.getUsername(), entity.getPassword(), authorities);
        String accessToken = utils.createToken(authentication);

        log.debug("Token JWT generado para el nuevo usuario: {}", username);
        return new AuthResponse(entity.getUsername(), "Usuario creado con éxito", accessToken, true);
    }

    private void validatePasswordsMatch(String password, String passwordRepeat) {
        if (!password.contentEquals(passwordRepeat)) {
            log.warn("Las contraseñas no coinciden.");
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
    }

    private Authentication authenticate(String username, String password) {
        log.debug("Autenticando usuario: {}", username);
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error("Credenciales inválidas para el usuario: {}", username);
            throw new BadCredentialsException("Invalid username or password.");
        }

        log.info("Usuario autenticado correctamente: {}", username);
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
