package com.findservices.serviceprovider.state.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.country.model.CountryDto;
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
public class StateDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

    @NotNull
    CountryDto country;
}
