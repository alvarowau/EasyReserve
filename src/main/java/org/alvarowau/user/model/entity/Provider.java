package org.alvarowau.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.model.value.UserProfile;

import java.time.LocalDateTime;

@Entity
@Table(name = "providers")
@NoArgsConstructor
public class Provider extends UserEntity {

    public Provider(String username, String password, String email, UserProfile userProfile) {
        super(username, password, RoleEnum.PROVIDER, email, userProfile);
    }
}