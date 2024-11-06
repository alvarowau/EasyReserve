package org.alvarowau.config.utils;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SecurityContextUtil {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    public String getUsernameToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }

    public String getAuthenticatedUsername(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return jwtTokenProvider.getUsernameFromToken(token.substring(7));
        }
        throw new RuntimeException("No hay un usuario autenticado en el contexto.");
    }

    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities()
                    .iterator().next()
                    .getAuthority().substring(5);
        }
        throw new RuntimeException("No hay un usuario autenticado en el contexto.");
    }

    public UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        } else if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return customUserDetailsService.loadUserByUsername(username);
        }
        throw new RuntimeException("No hay un usuario autenticado en el contexto.");
    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username;

            // Intenta obtener el nombre de usuario directamente del objeto Authentication
            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else {
                username = authentication.getName(); // Este es el nombre de usuario proporcionado al autenticarse
            }

            return username;
        }
        throw new RuntimeException("No hay un usuario autenticado en el contexto.");
    }

}
