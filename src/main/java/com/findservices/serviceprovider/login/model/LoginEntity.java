package com.findservices.serviceprovider.login.model;

import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity(name = "login")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_login_username", columnNames = {"username"})
})
@Setter
@EqualsAndHashCode
@Getter
public class LoginEntity implements UserDetails, Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role = RoleType.CLIENT;

    @OneToOne
    @MapsId
    @JoinColumn(name = "login", foreignKey = @ForeignKey(name = "fk_login_user_id"))
    private UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
