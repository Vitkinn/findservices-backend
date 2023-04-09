package com.mei.serviceprovider.state.model;

import com.mei.serviceprovider.country.model.CountryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static com.mei.serviceprovider.common.constants.TranslationConstants.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateDto {

    UUID id;

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

    @NotNull
    CountryDto country;
}
