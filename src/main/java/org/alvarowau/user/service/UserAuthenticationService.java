package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
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


    public LoginResponse authenticateUser(AuthLoginRequest request) {

        Authentication authentication = authenticate(request.username(), request.password());

        String accessToken = jwtTokenProvider.createToken(authentication);

        return new LoginResponse(request.username(), "Usuario autenticado con éxito", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new AuthenticationFailedException();
        }


        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationFailedException();
        }

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);

        // Guarda la autenticación en el contexto de seguridad
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        return authentication;
    }
}