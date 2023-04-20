package com.findservices.serviceprovider.serviceprovider.model;

import com.findservices.serviceprovider.city.model.CityEntity;
import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.List;
import java.util.UUID;

import static com.findservices.serviceprovider.common.constants.TranslationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "serviceProvider", uniqueConstraints = { //
        @UniqueConstraint(name = UK_STATE_NAME, columnNames = {"cnpj"}) //
})
public class ServiceProviderEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, name = "cnpj", length = 14)
    String cnpj;

    @NotNull
    @NotEmpty
    @Size(max = 11, min = 11)
    String cpf;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn( //
            name = "actuation_cities", //
            nullable = false, //
            foreignKey = @ForeignKey(name = FK_SERVICE_PROVIDER_CITY_ID) //
    )
    List<CityEntity> actuationCities;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn( //
            name = "user", //
                        foreignKey = @ForeignKey(name = FK_SERVICE_PROVIDER_USER_ID) //
    )
    UserEntity user;

    @Override
    public boolean isNew() {
        return id == null;
    }
}