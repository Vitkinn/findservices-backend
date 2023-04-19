package com.findservices.serviceprovider.state.model;

import com.findservices.serviceprovider.common.model.BaseEntity;
import com.findservices.serviceprovider.country.model.CountryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

import static com.findservices.serviceprovider.common.constants.TranslationConstants.FK_STATE_COUNTRY_ID;
import static com.findservices.serviceprovider.common.constants.TranslationConstants.UK_STATE_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "state", uniqueConstraints = { //
        @UniqueConstraint(name = UK_STATE_NAME, columnNames = {"name"}) //
})
public class StateEntity extends BaseEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, name = "name", length = 150)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( //
            name = "country", //
            nullable = false, //
            foreignKey = @ForeignKey(name = FK_STATE_COUNTRY_ID) //
    )
    CountryEntity country;

    @Override
    public boolean isNew() {
        return id == null;
    }
}