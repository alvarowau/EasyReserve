package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogService;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderAccountService extends AbstractUserAccountService<Provider> {

    private final ProviderRepository repository;

    public ProviderAccountService(ActionLogService actionLogService, ProviderRepository repository) {
        super(actionLogService);
        this.repository = repository;
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Provider> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Provider> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    protected Provider saveEntity(Provider entity) {
        return repository.save(entity);
    }

    public boolean existsRecords() {
        return repository.count() > 0;
    }
}