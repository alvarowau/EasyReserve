package org.alvarowau.model.dto.action;

import lombok.*;
import org.alvarowau.model.enums.ActionType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionSummary {
    private ActionType actionType;
    private String initiatorUsername;
    private String targetUsername;
    private String reason;
    private LocalDate actionDate;
}
