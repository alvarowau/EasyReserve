package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable = true;
}
