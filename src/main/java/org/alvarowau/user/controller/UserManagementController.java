package org.alvarowau.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogRequest;
import org.alvarowau.model.dto.action.AccountStatusChangeActionLogResponse;
import org.alvarowau.model.dto.action.UserAccountStatusChangeRequestByStaff;
import org.alvarowau.user.service.UserAccountAuthFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user account activation and deactivation.
 */
@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor
@Tag(name = "Gestión de cuentas de usuario", description = "Controlador para la activación y desactivación de cuentas de usuario")
public class UserManagementController {

    private final UserAccountAuthFacade userAccountAuthFacade;
    private final SecurityContextUtil securityContext;

    @Operation(
            summary = "Desactivar cuenta de usuario actual",
            description = "Permite a un usuario desactivar su propia cuenta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para registrar la acción de desactivación de la cuenta",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AccountStatusChangeActionLogRequest.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cuenta de usuario desactivada exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountStatusChangeActionLogResponse.class)
            )
    )
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER', 'PROVIDER')")
    @DeleteMapping("/deactivate/current")
    public ResponseEntity<AccountStatusChangeActionLogResponse> deactivateCurrentUserAccount(
            @RequestBody AccountStatusChangeActionLogRequest requestDelete) {
        return ResponseEntity.ok(userAccountAuthFacade.deactivateCurrentUserAccount(requestDelete));
    }


    @Operation(
            summary = "Desactivar cuenta de otro usuario (solo STAFF)",
            description = "Permite al personal (STAFF) desactivar la cuenta de otro usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para registrar la acción de desactivación de la cuenta de otro usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserAccountStatusChangeRequestByStaff.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cuenta de usuario desactivada por el personal exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountStatusChangeActionLogResponse.class)
            )
    )
    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/staff/deactivate-user")
    public ResponseEntity<AccountStatusChangeActionLogResponse> deactivateUserByStaffAccount(
            @RequestBody UserAccountStatusChangeRequestByStaff requestDelete) {
        return ResponseEntity.ok(userAccountAuthFacade.deactivateUserAccountByStaff(requestDelete));
    }


    @Operation(
            summary = "Activar cuenta de otro usuario (solo STAFF)",
            description = "Permite al personal (STAFF) activar la cuenta de otro usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para registrar la acción de activación de la cuenta de otro usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserAccountStatusChangeRequestByStaff.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cuenta de usuario activada por el personal exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountStatusChangeActionLogResponse.class)
            )
    )
    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/staff/activate-user")
    public ResponseEntity<AccountStatusChangeActionLogResponse> activateUserByStaffAccount(
            @RequestBody UserAccountStatusChangeRequestByStaff requestActivate) {
        return ResponseEntity.ok(userAccountAuthFacade.activateUserAccountByStaff(requestActivate));
    }
}
