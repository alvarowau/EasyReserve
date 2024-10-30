package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, BaseUserRepository<Customer> {
}
