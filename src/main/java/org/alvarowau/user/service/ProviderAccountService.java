package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogManagementService;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderAccountService extends AbstractUserAccountService<Provider> {

    private final ProviderRepository repository;

    public ProviderAccountService(ActionLogManagementService actionLogManagementService, ProviderRepository repository) {
        super(actionLogManagementService);
        this.repository = repository;
    }

    @Override
    public Optional<Provider> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    protected Provider saveEntity(Provider entity) {
        return repository.save(entity);
    }

    public boolean existsRecords() {
        return repository.count() > 0;
    }
}