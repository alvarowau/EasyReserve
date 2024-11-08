package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogManagementService;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerAccountService extends AbstractUserAccountService<Customer> {

    private final CustomerRepository repository;

    public CustomerAccountService(ActionLogManagementService actionLogManagementService, CustomerRepository repository) {
        super(actionLogManagementService);
        this.repository = repository;
    }

    @Override
    protected Customer saveEntity(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
