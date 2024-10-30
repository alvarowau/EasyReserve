package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;

public interface BaseUserRepository <T extends BaseUser> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<T> findByUsername(String username);
    Optional<T> findByEmail(String email);
}
