package org.alvarowau.model.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.alvarowau.model.enums.ActionType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(description = "Respuesta que representa el registro de un cambio en el estado de la cuenta, incluyendo detalles sobre el cambio.")
public class AccountStatusChangeActionLogResponse {

    @Schema(description = "Tipo de acción que se realizó, como activación o desactivación de la cuenta.", example = "ACTIVATE_ACCOUNT")
    private ActionType actionType;

    @Schema(description = "Nombre de usuario de quien realizó la acción de cambio de estado.", example = "adminUser")
    private String initiatorUsername;

    @Schema(description = "Nombre de usuario cuya cuenta fue modificada.", example = "targetUser")
    private String targetUsername;

    @Schema(description = "Razón por la cual se realizó el cambio en el estado de la cuenta.", example = "El usuario ha solicitado la reactivación de su cuenta.")
    private String reason;

    @Schema(description = "Indica si la acción de cambio de estado fue exitosa.", example = "true")
    private boolean successful;
}
