package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.common.CustomException;
import org.alvarowau.exception.user.StaffNotFoundException;
import org.alvarowau.exception.user.UserNotFoundException;
import org.alvarowau.model.dto.action.ActionLogRequestAccountStatusChange;
import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.user.model.entity.BaseUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final SecurityContextUtil securityContextUtil;
    private final CustomerService customerService;
    private final ProviderService providerService;
    private final StaffService staffService;
    private final CustomUserDetailsService customUserDetailsService;

    public ActionLogResponseAccountStatusChange deactivateCurrentUser(ActionLogRequestAccountStatusChange requestDelete) {
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        String role = securityContextUtil.getUserRole();
        ActionLogResponseAccountStatusChange responseDelete = createActionLogResponseDelete(requestDelete, authenticatedUsername, authenticatedUsername);
        return deactivateUser(responseDelete, role);
    }

    public ActionLogResponseAccountStatusChange deactivateCurrentUserByStaff(UserAccountStatusChangeRequestByStaff requestDelete, boolean active) {
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        Optional<Long> staffId = staffService.findIdByUsername(authenticatedUsername);

        if (staffId.isPresent()) {
            ActionLogResponseAccountStatusChange responseDelete = createActionLogResponseDelete(requestDelete, authenticatedUsername, requestDelete.usernameDelete());
            BaseUser base = customUserDetailsService.findUserByUsernameEntity(requestDelete.usernameDelete());
            if (base == null) {
                throw new UserNotFoundException(requestDelete.usernameDelete());
            }
            return deactivateUserByStaff(responseDelete, base.getRole().toString(), staffId.get(), active);
        }
        throw new StaffNotFoundException("No se pudo encontrar el staff con nombre de usuario: " + authenticatedUsername);
    }

    private ActionLogResponseAccountStatusChange createActionLogResponseDelete(ActionLogRequestAccountStatusChange requestDelete, String initiator, String target) {
        return ActionLogResponseAccountStatusChange.builder()
                .actionType(ActionType.DEACTIVATE)
                .initiatorUsername(initiator)
                .targetUsername(target)
                .reason(requestDelete.reason())
                .build();
    }

    private ActionLogResponseAccountStatusChange createActionLogResponseDelete(UserAccountStatusChangeRequestByStaff requestDelete, String initiator, String target) {
        return ActionLogResponseAccountStatusChange.builder()
                .actionType(ActionType.DEACTIVATE)
                .initiatorUsername(initiator)
                .targetUsername(target)
                .reason(requestDelete.reason())
                .build();
    }

    private ActionLogResponseAccountStatusChange deactivateUserByStaff(ActionLogResponseAccountStatusChange responseDelete, String rol, Long idStaff, boolean active) {
        return switch (rol) {
            case "STAFF" -> staffService.deactivateUserByStaff(responseDelete, idStaff, active);
            case "CUSTOMER" -> customerService.deactivateUserByStaff(responseDelete, idStaff, active);
            case "PROVIDER" -> providerService.deactivateUserByStaff(responseDelete, idStaff, active);
            default -> throw new CustomException("No se pudo hacer nada", "INVALID_ROLE");
        };
    }

    private ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange responseDelete, String rol) {
        return switch (rol) {
            case "STAFF" -> staffService.deactivateUser(responseDelete);
            case "CUSTOMER" -> customerService.deactivateUser(responseDelete);
            case "PROVIDER" -> providerService.deactivateUser(responseDelete);
            default -> throw new CustomException("No se pudo hacer nada", "INVALID_ROLE");
        };
    }
}
