package org.alvarowau.user.model.value;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class ContactInfo {
    private String primaryPhone;      // Número de teléfono principal
    private String secondaryPhone;    // Número de teléfono secundario (opcional)
    private String additionalEmail;    // Correo electrónico adicional (opcional)
}
