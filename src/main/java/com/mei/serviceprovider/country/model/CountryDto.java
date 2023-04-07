package com.mei.serviceprovider.country.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static com.mei.serviceprovider.common.constants.TranslationConstants.VALIDATION_COUNTRY_NAME_IS_REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryDto {

    UUID id;

    @NotEmpty(message = VALIDATION_COUNTRY_NAME_IS_REQUIRED)
    @Size(min = 2, message = "O nome deve ter no máximo 150 caracteres")
    String name;

}
