package org.alvarowau.user.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.model.value.UserProfile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer extends UserEntity {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Customer(String username, String password, String email, UserProfile userProfile) {
        super(username, password, RoleEnum.CUSTOMER, email, userProfile);
    }
}
