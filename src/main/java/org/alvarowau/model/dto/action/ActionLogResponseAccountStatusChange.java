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
        private ActionType actionType;
        private String initiatorUsername;
        private String targetUsername;
        private String reason;
        private boolean isSuccessful;
}
