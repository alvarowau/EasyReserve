package org.alvarowau.model.dto.action;

import org.alvarowau.model.enums.ActionType;

public record ActionLogDTO(
        ActionType actionType,
        Long userId,
        Long affectedUserId,
        String reason
) {}
