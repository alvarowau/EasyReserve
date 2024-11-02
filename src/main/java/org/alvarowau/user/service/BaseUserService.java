package org.alvarowau.user.service;

import org.alvarowau.model.dto.ActionLogResponseAccountStatusChange;
import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;

/**
 * Interface for common user-related service operations.
 *
 * @param <T> a type that extends BaseUser
 */
public interface BaseUserService<T extends BaseUser> {
    Optional<T> findByUsername(String username);
    Optional<T> findByEmail(String email);
    ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange delete);
}
