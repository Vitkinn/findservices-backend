package com.mei.serviceprovider.state.model;

import com.mei.serviceprovider.country.model.CountryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static com.mei.serviceprovider.common.constants.TranslationConstants.VALIDATION_STATE_COUNTRY_IS_REQUIRED;
import static com.mei.serviceprovider.common.constants.TranslationConstants.VALIDATION_STATE_NAME_IS_REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateDto {

    UUID id;

    @NotBlank(message = VALIDATION_STATE_NAME_IS_REQUIRED)
    @Size(min = 2, message = "O nome deve ter no máximo 150 caracteres")
    String name;

    @NotNull(message = VALIDATION_STATE_COUNTRY_IS_REQUIRED)
    CountryDto country;
}
