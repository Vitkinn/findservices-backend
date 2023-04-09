package com.mei.serviceprovider.address.model;

import com.mei.serviceprovider.city.model.CityEntity;
import com.mei.serviceprovider.common.constants.TranslationConstants;
import com.mei.serviceprovider.state.model.StateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import static com.mei.serviceprovider.common.constants.TranslationConstants.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {

    UUID id;

    @NotEmpty
    @NotNull
    @Size(max = 8)
    String cep;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    String neighborhood;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    String street;

    @NotNull
    @Min(value = 1)
    Integer houseNumber;

    @Size(max = 255)
    String complement;

    @ManyToOne
    @JoinColumn( //
            name = "city", //
            nullable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_ADDRESS_CITY) //
    )
    CityEntity city;

}
