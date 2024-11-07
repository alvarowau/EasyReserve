package org.alvarowau.user.model.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import org.alvarowau.model.value.Address;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserProfile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Embedded
    private PersonalInfo personalInfo;

    @Embedded
    private ContactInfo contactInfo;

    @Embedded
    private Address address;
}
