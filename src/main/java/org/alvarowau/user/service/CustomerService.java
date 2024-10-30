package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements BaseUserService<Customer> {

    private final CustomerRepository repository;

    @Override
    public Optional<Customer> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }
}
