package org.alvarowau.user.model.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import org.alvarowau.model.value.Address;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserProfile {

    @Embedded
    private PersonalInfo personalInfo; // Información personal

    @Embedded
    private ContactInfo contactInfo; // Información de contacto

    @Embedded
    private Address address; // Dirección del usuario
}
