package org.alvarowau.model.value;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
