package org.alvarowau.config.utils;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.AuthenticationFailedException;
import org.alvarowau.user.service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SecurityContextUtil {


    private final CustomUserDetailsService customUserDetailsService;


    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities()
                    .iterator().next()
                    .getAuthority().substring(5);
        }
        throw new AuthenticationFailedException();
    }

    public UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Usamos el patrón 'instanceof' con la variable 'userDetails'
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails;
            } else {
                String username = authentication.getName();
                return customUserDetailsService.loadUserByUsername(username);
            }
        }
        throw new AuthenticationFailedException();
    }


    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Usamos el patrón 'instanceof' con la variable 'userDetails'
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                return authentication.getName(); // Este es el nombre de usuario proporcionado al autenticarse
            }
        }
        throw new AuthenticationFailedException();
    }


}
