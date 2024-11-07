package org.alvarowau.user.controller;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogRequest;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogResponse;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user account activation and deactivation.
 */
@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserAccountAuthFacade userAccountAuthFacade;
    private final SecurityContextUtil securityContext;

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @DeleteMapping("/deactivate/current")
    public ResponseEntity<AccountStatusChangeActionLogResponse> deactivateCurrentUserAccount(
            @RequestBody AccountStatusChangeActionLogRequest requestDelete) {
        return ResponseEntity.ok(userAccountAuthFacade.deactivateCurrentUserAccount(requestDelete));
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/staff/deactivate-user")
    public ResponseEntity<AccountStatusChangeActionLogResponse> deactivateUserByStaffAccount(
            @RequestBody UserAccountStatusChangeRequestByStaff requestDelete) {
        return ResponseEntity.ok(userAccountAuthFacade.deactivateUserAccountByStaff(requestDelete));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/staff/activate-user")
    public ResponseEntity<AccountStatusChangeActionLogResponse> activateUserByStaffAccount(
            @RequestBody UserAccountStatusChangeRequestByStaff requestActivate) {
        return ResponseEntity.ok(userAccountAuthFacade.activateUserAccountByStaff(requestActivate));
    }
}
