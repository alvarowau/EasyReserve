package org.alvarowau.model.dto.action;

import lombok.*;
import org.alvarowau.model.enums.ActionType;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ActionLogResponseAccountStatusChange {
        private ActionType actionType;            // Tipo de acción realizada
        private String initiatorUsername;         // Nombre de usuario que realizó la acción
        private String targetUsername;             // Nombre de usuario afectado por la acción
        private String reason;                    // Motivo de la acción
        private boolean isSuccessful;               // Indica si la acción se realizó con éxito
}
