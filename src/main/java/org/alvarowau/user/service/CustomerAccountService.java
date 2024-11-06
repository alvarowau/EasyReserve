package org.alvarowau.user.service;

import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.repository.CustomerRepository;
import org.alvarowau.service.ActionLogService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerAccountService extends AbstractUserAccountService<Customer> {

    private final CustomerRepository repository;

    public CustomerAccountService(ActionLogService actionLogService, CustomerRepository repository) {
        super(actionLogService);
        this.repository = repository;
    }

    @Override
    protected Customer saveEntity(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
