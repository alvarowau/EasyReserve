package org.alvarowau.model.dto;

import org.alvarowau.model.enums.ActionType;

public record ActionLogDTO(
        ActionType actionType,            // Tipo de acción (usando el enum ActionType)
        Long userId,                      // ID del usuario que realizó la acción
        Long affectedUserId,              // ID del usuario afectado por la acción
        String reason                      // Motivo de la acción
) {}
