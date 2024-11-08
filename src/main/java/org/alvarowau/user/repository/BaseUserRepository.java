package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;

public interface BaseUserRepository<T extends BaseUser> {
    Optional<T> findByUsername(String username);
}