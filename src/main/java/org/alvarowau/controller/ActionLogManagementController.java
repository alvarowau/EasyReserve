package org.alvarowau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionSummary;
import org.alvarowau.service.ActionLogManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actions")
@RequiredArgsConstructor
@Tag(name = "Gestión de Logs de Acciones", description = "Controlador para consultar registros de acciones")
public class ActionLogManagementController {

    private final ActionLogManagementService actionLogManagementService;

    @Operation(
            summary = "Obtener logs por tipo de acción",
            description = "Permite a los usuarios con rol de STAFF obtener logs específicos basados en el tipo de acción",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de logs filtrada por tipo de acción",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ActionSummary.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF')")
    @GetMapping("/by-action-type/{actionType}")
    public ResponseEntity<List<ActionSummary>> getActionLogsByType(
            @PathVariable String actionType) {
        List<ActionSummary> actionSummaries = actionLogManagementService.getActionLogsByActionType(actionType);
        return ResponseEntity.ok(actionSummaries);
    }

    @Operation(
            summary = "Listar todos los logs de acciones",
            description = "Permite a los usuarios con rol de STAFF obtener todos los logs de acciones",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista completa de logs de acciones",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ActionSummary.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('STAFF')")
    @GetMapping
    public ResponseEntity<List<ActionSummary>> listAllActionLogs() {
        return ResponseEntity.ok(actionLogManagementService.getAllActionLogs());
    }
}
