package org.alvarowau.user.controller;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionLogRequestAccountStatusChange;
import org.alvarowau.model.dto.action.ActionLogResponseAccountStatusChange;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.user.service.UserAuthFacade;
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

    private final UserAuthFacade userAuthFacade;
    private final SecurityContextUtil securityContext;

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @DeleteMapping("/deactivate/current")
    public ResponseEntity<ActionLogResponseAccountStatusChange> deactivateCurrentUser(
            @RequestBody ActionLogRequestAccountStatusChange requestDelete) {
        return ResponseEntity.ok(userAuthFacade.deactivateCurrentUser(requestDelete));
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/staff/deactivate-user")
    public ResponseEntity<ActionLogResponseAccountStatusChange> deactivateUserByStaff(
            @RequestBody UserAccountStatusChangeRequestByStaff requestDelete) {
        return ResponseEntity.ok(userAuthFacade.deactivateUserByStaff(requestDelete));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/staff/activate-user")
    public ResponseEntity<ActionLogResponseAccountStatusChange> activateUserByStaff(
            @RequestBody UserAccountStatusChangeRequestByStaff requestActivate) {
        return ResponseEntity.ok(userAuthFacade.activateUserByStaff(requestActivate));
    }
}
