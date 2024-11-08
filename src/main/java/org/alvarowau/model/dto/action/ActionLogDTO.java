package org.alvarowau.model.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.enums.ActionType;

@Schema(description = "DTO que representa un registro de acción que involucra un usuario y un usuario afectado, incluyendo el tipo de acción y el motivo.")
public record ActionLogDTO(

        @Schema(description = "Tipo de acción realizada, como un cambio de estado de la cuenta o modificación de datos.", example = "DEACTIVATE_ACCOUNT")
        ActionType actionType,

        @Schema(description = "ID del usuario que realizó la acción.", example = "12345")
        Long userId,

        @Schema(description = "ID del usuario afectado por la acción.", example = "67890")
        Long affectedUserId,

        @Schema(description = "Razón detallada por la cual se realizó la acción.", example = "El usuario ha solicitado la desactivación de su cuenta.")
        String reason
) {}
