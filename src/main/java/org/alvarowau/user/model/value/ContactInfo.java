package org.alvarowau.user.model.value;

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
public class ContactInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String primaryPhone;
    private String secondaryPhone;
    private String additionalEmail;
}
