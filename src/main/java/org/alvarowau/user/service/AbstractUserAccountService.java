package org.alvarowau.user.service;

import org.alvarowau.model.dto.action.AccountStatusChangeActionLogResponse;
import org.alvarowau.model.dto.action.ActionLogDTO;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.service.ActionLogManagementService;
import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;


public abstract class AbstractUserAccountService<T extends BaseUser> implements BaseUserService<T> {

    protected final ActionLogManagementService actionLogManagementService;

    protected AbstractUserAccountService(ActionLogManagementService actionLogManagementService) {
        this.actionLogManagementService = actionLogManagementService;
    }

    protected abstract T saveEntity(T entity);

    @Override
    public AccountStatusChangeActionLogResponse deactivateUserAccount(AccountStatusChangeActionLogResponse delete) {
        return deactivateUserAccountInternal(delete, null, false);
    }

    public AccountStatusChangeActionLogResponse deactivateUserAccountByStaff(AccountStatusChangeActionLogResponse delete, Long staffId, boolean active) {
        return deactivateUserAccountInternal(delete, staffId, active);
    }

    private AccountStatusChangeActionLogResponse deactivateUserAccountInternal(AccountStatusChangeActionLogResponse delete, Long staffId, boolean active) {
        Optional<T> optionalEntity = findByUsername(staffId == null ? delete.getInitiatorUsername() : delete.getTargetUsername());
        if (optionalEntity.isPresent()) {
            T entity = optionalEntity.get();
            entity.setEnabled(active);
            entity = saveEntity(entity);

            if (!entity.isEnabled()) {
                ActionLogDTO action = new ActionLogDTO(
                        active ? ActionType.REACTIVATE : ActionType.DEACTIVATE,
                        staffId != null ? staffId : entity.getId(),
                        entity.getId(),
                        delete.getReason()
                );
                actionLogManagementService.saveActionLog(action);
                delete.setSuccessful(true);
                return delete;
            }
        }
        delete.setSuccessful(false);
        return delete;
    }
}
