package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.model.enums.BookingStatus;
import org.alvarowau.user.model.entity.Customer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(unique = true, nullable = false)
    private String bookingNumber;

    @ManyToOne // Relación muchos a uno con Customer
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
