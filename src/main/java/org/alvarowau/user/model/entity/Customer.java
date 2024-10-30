package org.alvarowau.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.model.value.UserProfile;

@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer extends UserEntity {

    public Customer(String username, String password, String email, UserProfile userProfile) {
        super(username, password, RoleEnum.CUSTOMER, email, userProfile);
    }
}
