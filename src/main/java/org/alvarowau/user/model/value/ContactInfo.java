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
    private String primaryPhone;
    private String secondaryPhone;
    private String additionalEmail;
}
