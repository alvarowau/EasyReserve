package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.common.CustomException;
import org.alvarowau.exception.user.StaffNotFoundException;
import org.alvarowau.exception.user.UserNotFoundException;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogRequest;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogResponse;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.user.model.entity.BaseUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountManagementService {

    private final SecurityContextUtil securityContextUtil;
    private final CustomerAccountService customerService;
    private final ProviderAccountService providerService;
    private final StaffAccountService staffService;
    private final CustomUserDetailsService customUserDetailsService;

    public AccountStatusChangeActionLogResponse deactivateCurrentUserAccount(AccountStatusChangeActionLogRequest requestDelete) {
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        String role = securityContextUtil.getUserRole();
        AccountStatusChangeActionLogResponse responseDelete = createActionLogResponseForDeactivation(requestDelete, authenticatedUsername, authenticatedUsername);
        return deactivateUserAccount(responseDelete, role);
    }

    public AccountStatusChangeActionLogResponse deactivateUserAccountByStaff(UserAccountStatusChangeRequestByStaff requestDelete, boolean active) {
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        Optional<Long> staffId = staffService.findIdByUsername(authenticatedUsername);

        if (staffId.isPresent()) {
            AccountStatusChangeActionLogResponse responseDelete = createActionLogResponseForDeactivation(requestDelete, authenticatedUsername, requestDelete.usernameToDelete());
            BaseUser base = customUserDetailsService.findUserByUsernameEntity(requestDelete.usernameToDelete());
            if (base == null) {
                throw new UserNotFoundException(requestDelete.usernameToDelete());
            }
            return deactivateUserAccountByStaff(responseDelete, base.getRole().toString(), staffId.get(), active);
        }
        throw new StaffNotFoundException("No se pudo encontrar el staff con nombre de usuario: " + authenticatedUsername);
    }

    private AccountStatusChangeActionLogResponse createActionLogResponseForDeactivation(AccountStatusChangeActionLogRequest requestDelete, String initiator, String target) {
        return AccountStatusChangeActionLogResponse.builder()
                .actionType(ActionType.DEACTIVATE)
                .initiatorUsername(initiator)
                .targetUsername(target)
                .reason(requestDelete.reason())
                .build();
    }

    private AccountStatusChangeActionLogResponse createActionLogResponseForDeactivation(UserAccountStatusChangeRequestByStaff requestDelete, String initiator, String target) {
        return AccountStatusChangeActionLogResponse.builder()
                .actionType(ActionType.DEACTIVATE)
                .initiatorUsername(initiator)
                .targetUsername(target)
                .reason(requestDelete.deletionReason())
                .build();
    }

    private AccountStatusChangeActionLogResponse deactivateUserAccountByStaff(AccountStatusChangeActionLogResponse responseDelete, String rol, Long idStaff, boolean active) {
        return switch (rol) {
            case "STAFF" -> staffService.deactivateUserAccountByStaff(responseDelete, idStaff, active);
            case "CUSTOMER" -> customerService.deactivateUserAccountByStaff(responseDelete, idStaff, active);
            case "PROVIDER" -> providerService.deactivateUserAccountByStaff(responseDelete, idStaff, active);
            default -> throw new CustomException("No se pudo hacer nada", "INVALID_ROLE");
        };
    }

    private AccountStatusChangeActionLogResponse deactivateUserAccount(AccountStatusChangeActionLogResponse responseDelete, String rol) {
        return switch (rol) {
            case "STAFF" -> staffService.deactivateUserAccount(responseDelete);
            case "CUSTOMER" -> customerService.deactivateUserAccount(responseDelete);
            case "PROVIDER" -> providerService.deactivateUserAccount(responseDelete);
            default -> throw new CustomException("No se pudo hacer nada", "INVALID_ROLE");
        };
    }
}
