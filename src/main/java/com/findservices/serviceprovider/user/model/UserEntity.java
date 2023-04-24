package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.address.model.AddressEntity;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.List;
import java.util.UUID;

import static com.findservices.serviceprovider.common.constants.TranslationConstants.FK_USER_ADDRESS_ID;

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

    @Column(nullable = false, name = "last_name", length = 11)
    String lastName;

    @Column(name = "user_photo_url", length = 300)
    String userPhotoUrl;

    @Column(name = "cpf", length = 11)
    String cpf;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    List<AddressEntity> address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    ServiceProviderEntity serviceProvider;

    @Override
    public boolean isNew() {
        return id == null;
    }
}