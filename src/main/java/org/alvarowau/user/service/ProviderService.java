package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogService;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderService extends AbstractBaseUserService<Provider> {

    private final ProviderRepository repository;

    public ProviderService(ActionLogService actionLogService, ProviderRepository repository) {
        super(actionLogService);
        this.repository = repository;
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
}
