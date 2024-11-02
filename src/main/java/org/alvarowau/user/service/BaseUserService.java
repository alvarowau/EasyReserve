package org.alvarowau.user.service;

import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;


public interface BaseUserService<T extends BaseUser> {
    Optional<T> findByUsername(String username);
    Optional<T> findByEmail(String email);
    ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange delete);
}
