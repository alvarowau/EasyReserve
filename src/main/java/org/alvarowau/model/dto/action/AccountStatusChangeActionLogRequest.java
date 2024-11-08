package org.alvarowau.model.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para registrar un cambio en el estado de la cuenta, incluyendo el motivo del cambio.")
public record AccountStatusChangeActionLogRequest(

        @Schema(description = "El motivo por el cual se realizó el cambio en el estado de la cuenta.", example = "El usuario ha solicitado la cancelación de su cuenta.")
        String reason
) {
}
