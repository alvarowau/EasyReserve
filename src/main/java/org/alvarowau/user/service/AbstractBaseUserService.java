package org.alvarowau.user.service;

import org.alvarowau.model.dto.action.ActionLogDTO;
import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.service.ActionLogService;
import org.alvarowau.user.model.entity.BaseUser;

import java.util.Optional;

public abstract class AbstractBaseUserService<T extends BaseUser> implements BaseUserService<T> {

    protected final ActionLogService actionLogService;

    protected AbstractBaseUserService(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }

    protected abstract T saveEntity(T entity);

    @Override
    public ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange delete) {
        return deactivateUser(delete, null, false);
    }

    public ActionLogResponseAccountStatusChange deactivateUserByStaff(ActionLogResponseAccountStatusChange delete, Long staffId, boolean active) {
        return deactivateUser(delete, staffId, active);
    }

    private ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange delete, Long staffId, boolean active) {
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
                System.out.println(action);
                actionLogService.saveAction(action);
                delete.setSuccessful(true);
                return delete;
            }
        }
        delete.setSuccessful(false);
        return delete;
    }
}
