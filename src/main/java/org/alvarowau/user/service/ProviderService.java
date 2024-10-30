package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderService implements BaseUserService<Provider> {

    private final ProviderRepository repository;


    @Override
    public Optional<Provider> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Provider> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Provider save(Provider provider) {
        return repository.save(provider);
    }
}
