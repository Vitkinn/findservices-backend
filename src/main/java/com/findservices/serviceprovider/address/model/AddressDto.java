package com.findservices.serviceprovider.address.model;

import com.findservices.serviceprovider.city.model.CityDto;
import com.findservices.serviceprovider.city.model.CityEntity;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.common.model.IdDto;
import com.findservices.serviceprovider.user.model.UserDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto extends BaseDto {

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

    @Valid
    @NotNull
    IdDto city;

    @Valid
    @NotNull
    IdDto user;

}
