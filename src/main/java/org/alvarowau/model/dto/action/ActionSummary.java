package org.alvarowau.model.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.alvarowau.model.enums.ActionType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Resumen de una acción realizada por un usuario, incluyendo detalles sobre el tipo de acción, el iniciador, el objetivo, la razón y la fecha.")
public class ActionSummary {
    @Schema(description = "Tipo de acción realizada (por ejemplo, activación de cuenta, cambio de rol, etc.).", example = "ACTIVATE_ACCOUNT")
    private ActionType actionType;

    @Schema(description = "Nombre de usuario del iniciador de la acción.", example = "admin_user")
    private String initiatorUsername;

    @Schema(description = "Nombre de usuario del objetivo de la acción.", example = "target_user")
    private String targetUsername;

    @Schema(description = "Razón detallada de la acción realizada.", example = "El usuario solicitó la activación de su cuenta.")
    private String reason;

    @Schema(description = "Fecha en la que se realizó la acción.", example = "2024-11-08")
    private LocalDate actionDate;
}
