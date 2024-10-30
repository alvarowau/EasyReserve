package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long>, BaseUserRepository<Provider> {
}
