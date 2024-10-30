package org.alvarowau.user.model.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstName;  // Nombre
    private String lastName;   // Apellidos
    private String idDocument; // Documento de identidad
}
