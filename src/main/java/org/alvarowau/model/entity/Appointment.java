package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alvarowau.user.model.entity.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime; // Hora de inicio de la cita
    private LocalDateTime endTime;   // Hora de fin de la cita

    private LocalDate date;
    private boolean isAvailable = true; // Disponibilidad inicial

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Cliente que reserva

    private String serviceName; // Nombre del servicio
    private String trackingNumber;   // Número de seguimiento
}

