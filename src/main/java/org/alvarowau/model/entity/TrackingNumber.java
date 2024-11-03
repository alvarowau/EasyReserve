package org.alvarowau.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingNumber {
    @Id
    private Long id = 1L; // Único para que haya solo una fila
    private int lastNumber;
}