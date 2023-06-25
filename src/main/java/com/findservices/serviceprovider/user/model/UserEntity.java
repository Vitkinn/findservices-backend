package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.address.model.AddressEntity;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, name = "name", length = 150)
    String name;

    @Column(nullable = false, name = "last_name", length = 150)
    String lastName;

    @Column(name = "user_photo_url", length = 4000)
    String userPhotoUrl;

    @Column(name = "cpf", length = 11)
    String cpf;

    @Column(name = "create_account_date", nullable = false)
    LocalDate createAccountDate;

    @Column(name = "phone", nullable = false)
    String phone;

    @OneToMany
    @JoinColumn(name = "user_id")
    List<AddressEntity> address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", updatable = false)
    ServiceProviderEntity serviceProvider;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private LoginEntity login;

    @Override
    public boolean isNew() {
        return id == null;
    }
}