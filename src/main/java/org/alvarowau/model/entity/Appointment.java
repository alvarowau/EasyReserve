package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.user.model.entity.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private LocalDate date;
    private boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private ServiceSchedule serviceSchedule;

    @Column(unique = true, nullable = false)
    private String trackingNumber;
}

