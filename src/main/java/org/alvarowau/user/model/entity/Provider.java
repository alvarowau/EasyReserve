package org.alvarowau.user.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.model.value.UserProfile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "providers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Provider extends UserEntity {

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOffering> serviceOfferings = new ArrayList<>();

    public Provider(String username, String password, String email, UserProfile userProfile) {
        super(username, password, RoleEnum.PROVIDER, email, userProfile);
    }
}