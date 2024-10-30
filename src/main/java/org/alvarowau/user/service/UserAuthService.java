package org.alvarowau.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.dto.mapper.UserEntityMapper;
import org.alvarowau.user.model.entity.UserEntity;
import org.alvarowau.user.model.entity.enums.RoleEnum;
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

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final CustomerService customerService;
    private final ProviderService providerService;
    private final StaffService staffService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public LoginResponse authenticateUser(AuthLoginRequest request) {
        ensurePasswordsMatch(request.password(), request.passwordRepeat());

        Authentication authentication = authenticate(request.username(), request.password());
        String accessToken = jwtTokenProvider.createToken(authentication);

        return new LoginResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
    }

    private void ensurePasswordsMatch(String password, String passwordRepeat) {
        if (!password.contentEquals(passwordRepeat)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    public LoginResponse registerUser(@Valid UserRegistrationRequest request, RoleEnum role) {
        ensurePasswordsMatch(request.password(), request.passwordRepeat());

        UserEntity userEntity;
        switch (role) {
            case CUSTOMER:
                userEntity = customerService.save(userEntityMapper.convertToCustomer(request));
                break;
            case PROVIDER:
                userEntity = providerService.save(userEntityMapper.convertToProvider(request));
                break;
            case STAFF:
                userEntity = staffService.save(userEntityMapper.convertToStaff(request));
                break;
            default:
                throw new IllegalArgumentException("Rol no válido para crear el usuario");
        }

        return buildAuthResponse(userEntity);
    }

    private LoginResponse buildAuthResponse(UserEntity entity) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + entity.getRole().name())
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                entity.getUsername(), entity.getPassword(), authorities);
        String accessToken = jwtTokenProvider.createToken(authentication);
        return new LoginResponse(entity.getUsername(), "Usuario creado con éxito", accessToken, true);
    }

    private UserDetails findUserByUsername(String username) {
        UserDetails user = customerService.findByUsername(username)
                .orElse(null);

        if (user == null) {
            user = providerService.findByUsername(username)
                    .orElse(null);
        }

        if (user == null) {
            user = staffService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        }

        return user;
    }


}
