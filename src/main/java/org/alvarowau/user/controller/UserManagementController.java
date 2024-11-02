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

@RestController
@RequestMapping("/deactivate")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserAuthFacade userAuthFacade;
    private final SecurityContextUtil securityContext;

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @DeleteMapping("/current")
    public ResponseEntity<ActionLogResponseAccountStatusChange> deactivateCurrentUser(@RequestHeader("Authorization") String token, @RequestBody ActionLogRequestAccountStatusChange requestDelete) {
        return ResponseEntity.ok(userAuthFacade.deactivateCurrentUser(token, requestDelete));
    }

    @PreAuthorize("hasAnyRole('STAFF')")
    @DeleteMapping("/staff/deactivate-user")
    public ResponseEntity<ActionLogResponseAccountStatusChange> deactivateUserByStaff(
            @RequestHeader("Authorization") String token,
            @RequestBody UserAccountStatusChangeRequestByStaff requestDelete) {
        return ResponseEntity.ok(userAuthFacade.deactivateUserByStaff(token, requestDelete));
    }

    @PreAuthorize("hasAnyRole('STAFF')")
    @PostMapping("/staff/activate-user")  // Cambié a POST para la activación
    public ResponseEntity<ActionLogResponseAccountStatusChange> activateUserByStaff(
            @RequestHeader("Authorization") String token,
            @RequestBody UserAccountStatusChangeRequestByStaff requestDelete) {
        return ResponseEntity.ok(userAuthFacade.activateUserByStaff(token, requestDelete)); // Aquí se asume que 'true' indica activar.
    }
}
