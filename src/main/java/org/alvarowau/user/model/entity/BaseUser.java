package org.alvarowau.user.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUser implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public BaseUser(String username, String password, RoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isEnabled = true; // Por defecto habilitado
        this.accountNoExpired = true; // Por defecto no expirada
        this.accountNoLocked = false; // Por defecto no bloqueada
        this.credentialNoExpired = true; // Por defecto no expirada
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
