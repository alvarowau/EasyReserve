package org.alvarowau.model.entity;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable = true;
}
