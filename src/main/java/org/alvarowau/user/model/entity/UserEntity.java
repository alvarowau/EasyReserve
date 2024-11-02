package org.alvarowau.user.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.model.value.UserProfile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class UserEntity extends BaseUser {

    @Column(unique = true, nullable = false)
    private String email;

    @Embedded
    private UserProfile userProfile;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public UserEntity(String username, String password, RoleEnum role, String email, UserProfile userProfile) {
        super(username, password, role);
        this.email = email;
        this.userProfile = userProfile;
    }
}
