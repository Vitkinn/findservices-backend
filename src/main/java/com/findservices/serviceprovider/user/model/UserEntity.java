package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.address.model.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( //
            name = "address", //
            nullable = false, //
            foreignKey = @ForeignKey(name = FK_USER_ADDRESS_ID) //
    )
    AddressEntity address;

    @Override
    public boolean isNew() {
        return id == null;
    }
}