package org.alvarowau.user.service;

import org.alvarowau.model.dto.action.AccountStatusChangeActionLogResponse;
import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;

public interface BaseUserService<T extends BaseUser> {
    Optional<T> findByUsername(String username);

    AccountStatusChangeActionLogResponse deactivateUserAccount(AccountStatusChangeActionLogResponse delete);
}
