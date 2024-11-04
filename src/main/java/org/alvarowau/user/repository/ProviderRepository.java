package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProviderRepository extends JpaRepository<Provider, Long>, BaseUserRepository<Provider> {
    @Query("SELECT COUNT(p) > 0 FROM Provider p")
    boolean existsAny();
}
