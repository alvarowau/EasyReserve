package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.entity.BaseUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CustomUserDetailsService implements UserDetailsService {
    private final CustomerAccountService customerService;
    private final ProviderAccountService providerService;
    private final StaffAccountService staffService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = findUserByUsername(username);
        if (user == null || user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException(username + " no tiene autoridades asignadas");
        }
        return user;
    }

    public UserDetails findUserByUsername(String username) {
        UserDetails user = customerService.findByUsername(username)
                .orElse(null);

        if (user == null) {
            user = providerService.findByUsername(username)
                    .orElse(null);
        }

        if (user == null) {
            user = staffService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
        }

        return user;
    }

    public BaseUser findUserByUsernameEntity(String username) {
        BaseUser user = customerService.findByUsername(username)
                .orElse(null);

        if (user == null) {
            user = providerService.findByUsername(username)
                    .orElse(null);
        }

        if (user == null) {
            user = staffService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
        }

        return user;
    }

}