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

    private final CustomerService customerService;
    private final ProviderService providerService;
    private final StaffService staffService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
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
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
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
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        }

        return user;
    }
}
