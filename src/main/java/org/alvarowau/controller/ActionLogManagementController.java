package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.action.ActionSummary;
import org.alvarowau.service.ActionLogService;
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
public class ActionLogManagementController {

    private final ActionLogService actionLogService;

    @PreAuthorize("hasAnyRole('STAFF')")
    @GetMapping("/by-action-type/{actionType}")
    public ResponseEntity<List<ActionSummary>> getActionLogsByType(
            @PathVariable String actionType) {
        List<ActionSummary> actionSummaries = actionLogService.getActionLogsByActionType(actionType);
        return ResponseEntity.ok(actionSummaries);
    }

    @PreAuthorize("hasAnyRole('STAFF')")
    @GetMapping
    public ResponseEntity<List<ActionSummary>> listAllActionLogs() {
        return ResponseEntity.ok(actionLogService.getAllActionLogs());
    }
}
