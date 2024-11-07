package org.alvarowau.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TimeSlot implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable = true;
}
