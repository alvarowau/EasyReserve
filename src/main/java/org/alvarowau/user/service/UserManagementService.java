package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.StaffNotFoundException;
import org.alvarowau.exception.user.UserNotFoundException;
import org.alvarowau.model.dto.ActionLogRequestAccountStatusChange;
import org.alvarowau.model.dto.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.user.model.entity.BaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.alvarowau.user.service.util.SecurityContextUtil;
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
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);


    public ActionLogResponseAccountStatusChange deactivateCurrentUser(String token, ActionLogRequestAccountStatusChange requestDelete) {
        // Obtén el nombre de usuario del contexto autenticado
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        String role = securityContextUtil.getUserRole();
        String userNameToken = securityContextUtil.getUsernameToken(token.substring(7));

        // Comparación con el nombre de usuario autenticado
        if (userNameToken.equals(authenticatedUsername)) {
            logger.debug("El usuario autenticado coincide con el usuario objetivo.");
            ActionLogResponseAccountStatusChange responseDelete = createActionLogResponseDelete(requestDelete, authenticatedUsername, authenticatedUsername);
            return deactivateUser(responseDelete, role);
        } else {
            logger.error("El nombre de usuario objetivo no coincide con el usuario autenticado: {} vs {}", userNameToken, authenticatedUsername);
            throw new UserNotFoundException(userNameToken);
        }
    }

    public ActionLogResponseAccountStatusChange deactivateCurrentUserByStaff(String token, UserAccountStatusChangeRequestByStaff requestDelete, boolean active) {
        String authenticatedUsername = securityContextUtil.getAuthenticatedUserDetails().getUsername();
        logger.info("Nombre de usuario autenticado (staff): {}", authenticatedUsername);
        Optional<Long> staffId = staffService.findIdByUsername(authenticatedUsername);
        String userNameToken = securityContextUtil.getUsernameToken(token.substring(7));
        logger.info("El nombre del token es: {}", userNameToken);
        logger.info("Staff ID encontrado: {}", staffId.orElse(null));
        logger.info("Nombre de usuario a desactivar: {}", requestDelete.usernameDelete());

        if (staffId.isPresent()) {
            logger.info("El usuario autenticado tiene permisos para desactivar a: {}", requestDelete.usernameDelete());
            ActionLogResponseAccountStatusChange responseDelete = createActionLogResponseDelete(requestDelete, authenticatedUsername, requestDelete.usernameDelete());
            logger.info(responseDelete.toString());
            BaseUser base = customUserDetailsService.findUserByUsernameEntity(requestDelete.usernameDelete());
            if (base == null) {
                throw new UserNotFoundException(requestDelete.usernameDelete());
            }
            logger.debug("Autoridades del usuario a desactivar: {}", base.getAuthorities());
            return deactivateUserByStaff(responseDelete, base.getRole().toString(), staffId.get(), active);
        }

        logger.error("No se pudo encontrar el staff con nombre de usuario: {}", authenticatedUsername);
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
            case "STAFF" -> staffService.deactivateUserByStaff(responseDelete, idStaff,active);
            case "CUSTOMER" -> customerService.deactivateUserByStaff(responseDelete, idStaff, active);
            case "PROVIDER" -> providerService.deactivateUserByStaff(responseDelete, idStaff, active);
            default -> throw new RuntimeException("No se pudo hacer nada");
        };
    }



    private ActionLogResponseAccountStatusChange deactivateUser(ActionLogResponseAccountStatusChange responseDelete, String rol) {
        return switch (rol) {
            case "STAFF" -> staffService.deactivateUser(responseDelete);
            case "CUSTOMER" -> customerService.deactivateUser(responseDelete);
            case "PROVIDER" -> providerService.deactivateUser(responseDelete);
            default -> throw new RuntimeException("No se pudo hacer nada");
        };
    }
}
