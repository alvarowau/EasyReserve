package org.alvarowau.model.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    private String street;     // Calle
    private String city;       // Ciudad
    private String state;      // Estado o provincia
    private String zipCode;    // Código postal
    private String country;     // País
}
