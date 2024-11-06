package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, BaseUserRepository<Customer> {
}
