package com.findservices.serviceprovider.country.model;

import com.findservices.serviceprovider.common.model.BaseDto;
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
public class CountryDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

}
