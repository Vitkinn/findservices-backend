package com.findservices.serviceprovider.address.model;

import com.findservices.serviceprovider.city.model.CityEntity;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, length = 8)
    String cep;
    @Column(nullable = false)
    String neighborhood;
    @Column(nullable = false)
    String street;
    @Column(nullable = false)
    Integer houseNumber;
    @Column
    String complement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( //
            name = "city", //
            nullable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_ADDRESS_CITY) //
    )
    CityEntity city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( //
            nullable = false, //
            updatable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_ADDRESS_USER) //
    )
    UserEntity user;

    @Override
    public boolean isNew() {
        return id == null;
    }
}