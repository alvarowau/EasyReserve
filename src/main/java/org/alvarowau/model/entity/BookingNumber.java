package org.alvarowau.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BookingNumber {

    @Id
    private Long id = 1L;

    private int lastNumber;
}