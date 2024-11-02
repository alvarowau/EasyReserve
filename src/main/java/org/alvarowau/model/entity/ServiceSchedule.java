package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering serviceOffering;

    @OneToMany(mappedBy = "serviceSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots; // Lista de intervalos de tiempo disponibles
}