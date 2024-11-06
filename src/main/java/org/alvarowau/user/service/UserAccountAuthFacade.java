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
public class UserAccountAuthFacade {

    private final UserAuthenticationService userAuthenticationService;
    private final UserSignUpService userSignUpService;
    private final UserAccountManagementService userAccountManagementService;

    public LoginResponse authenticateUserLogin(AuthLoginRequest request) {
        return userAuthenticationService.authenticateUser(request);
    }

    public LoginResponse registerUserAccount(@Valid UserRegistrationRequest request, RoleEnum role) {
        return userSignUpService.registerUser(request, role);
    }

    public ActionLogResponseAccountStatusChange deactivateCurrentUserAccount(ActionLogRequestAccountStatusChange requestDelete) {
        return userAccountManagementService.deactivateCurrentUserAccount(requestDelete);
    }

    public ActionLogResponseAccountStatusChange deactivateUserAccountByStaff(UserAccountStatusChangeRequestByStaff requestDelete) {
        return userAccountManagementService.deactivateUserAccountByStaff(requestDelete, false);
    }

    public ActionLogResponseAccountStatusChange activateUserAccountByStaff(UserAccountStatusChangeRequestByStaff requestDelete) {
        return userAccountManagementService.deactivateUserAccountByStaff(requestDelete, true);
    }
}