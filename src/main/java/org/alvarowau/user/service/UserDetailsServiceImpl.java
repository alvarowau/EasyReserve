package org.alvarowau.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.config.util.JwtUtils;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementación del servicio de detalles de usuario que maneja la autenticación y la creación de usuarios.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // Repositorio para interactuar con la base de datos de usuarios
    private final JwtUtils utils; // Utilidad para manejar tokens JWT
    private final PasswordEncoder passwordEncoder; // Codificador de contraseñas

    /**
     * Carga un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario
     * @return un objeto UserDetails que contiene los detalles del usuario
     * @throws UsernameNotFoundException si el usuario no se encuentra
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obtiene el usuario de la base de datos o lanza una excepción si no se encuentra
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Configura los roles del usuario con el prefijo "ROLE_"
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        // Retorna un objeto UserDetails con la información del usuario
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

    /**
     * Autentica a un usuario con el nombre de usuario y la contraseña proporcionados.
     *
     * @param request el objeto de solicitud de inicio de sesión que contiene el nombre de usuario y la contraseña
     * @return la respuesta de autenticación que contiene el token de acceso
     */
    public AuthResponse loginUser(AuthLoginRequest request) {
        // Valida que las contraseñas coincidan
        validatePasswordsMatch(request.password(), request.passwordRepeat());

        // Autentica al usuario y genera el token JWT
        Authentication authentication = authenticate(request.username(), request.password());
        String accessToken = utils.createToken(authentication);

        // Retorna la respuesta de autenticación
        return new AuthResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param request el objeto de solicitud que contiene los datos del nuevo usuario
     * @param rol     el rol del nuevo usuario
     * @return la respuesta que contiene el nombre de usuario y el token de acceso
     */
    public AuthResponse createUser(@Valid AuthCreateUser request, RoleEnum rol) {
        // Valida que las contraseñas coincidan
        validatePasswordsMatch(request.password(), request.passwordRepeat());
        String username = request.username();
        String password = request.password();

        // Crea un nuevo objeto UserEntity y establece sus propiedades
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password)); // Codifica la contraseña
        userEntity.setRole(rol);
        userEntity.setEnabled(true);
        userEntity.setAccountNoExpired(true);
        userEntity.setCredentialNoExpired(true);
        userEntity.setAccountNoLocked(true);

        // Guarda el nuevo usuario en la base de datos
        UserEntity entity = userRepository.save(userEntity);

        // Configura las autoridades del usuario
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + entity.getRole().name())
        );

        // Crea un objeto de autenticación y genera el token JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                entity.getUsername(), entity.getPassword(), authorities);
        String accessToken = utils.createToken(authentication);

        // Retorna la respuesta de creación del usuario
        return new AuthResponse(entity.getUsername(), "Usuario creado con éxito", accessToken, true);
    }

    /**
     * Valida que las contraseñas proporcionadas coincidan.
     *
     * @param password       la contraseña
     * @param passwordRepeat la contraseña repetida para comparación
     * @throws IllegalArgumentException si las contraseñas no coinciden
     */
    private void validatePasswordsMatch(String password, String passwordRepeat) {
        if (!password.contentEquals(passwordRepeat)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
    }

    /**
     * Autentica al usuario usando su nombre de usuario y contraseña.
     *
     * @param username el nombre de usuario
     * @param password la contraseña
     * @return el objeto de autenticación del usuario
     * @throws BadCredentialsException si las credenciales son inválidas
     */
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username); // Carga el usuario

        // Verifica si las credenciales son válidas
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        // Retorna un objeto de autenticación
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
