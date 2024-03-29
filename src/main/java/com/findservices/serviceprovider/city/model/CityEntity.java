package com.findservices.serviceprovider.city.model;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
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
@Table(name = "city", uniqueConstraints = { //
        @UniqueConstraint(name = TranslationConstants.UK_CITY_NAME, columnNames = {"name"}) //
})
public class CityEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, name = "name", length = 150)
    String name;

    @Override
    public boolean isNew() {
        return id == null;
    }
}