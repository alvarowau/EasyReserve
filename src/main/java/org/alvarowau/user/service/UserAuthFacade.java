package org.alvarowau.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionLogRequestAccountStatusChange;
import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.AuthLoginRequest;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthFacade {

    private final UserAuthenticationService userAuthenticationService;
    private final UserSignUpService userSignUpService;
    private final UserManagementService userManagementService;

    public LoginResponse authenticateUser(AuthLoginRequest request) {
        return userAuthenticationService.authenticateUser(request);
    }

    public LoginResponse registerUser(@Valid UserRegistrationRequest request, RoleEnum role) {
        return userSignUpService.registerUser(request, role);
    }

    public ActionLogResponseAccountStatusChange deactivateCurrentUser(String token , ActionLogRequestAccountStatusChange requestDelete) {
        return userManagementService.deactivateCurrentUser(token, requestDelete);
    }

    public ActionLogResponseAccountStatusChange deactivateUserByStaff(String token, UserAccountStatusChangeRequestByStaff requestDelete) {
        return userManagementService.deactivateCurrentUserByStaff(token,requestDelete, false);
    }

    public ActionLogResponseAccountStatusChange activateUserByStaff(String token, UserAccountStatusChangeRequestByStaff requestDelete) {
        return userManagementService.deactivateCurrentUserByStaff(token,requestDelete, true);
    }
}
