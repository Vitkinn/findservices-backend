package com.mei.serviceprovider.city.model;

import com.mei.serviceprovider.state.model.StateDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static com.mei.serviceprovider.common.constants.TranslationConstants.VALIDATION_CITY_NAME_IS_REQUIRED;
import static com.mei.serviceprovider.common.constants.TranslationConstants.VALIDATION_CITY_STATE_IS_REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityDto {

    UUID id;

    @NotEmpty(message = VALIDATION_CITY_NAME_IS_REQUIRED)
    @Size(min = 2, message = "O nome deve ter no máximo 150 caracteres")
    String name;

    @NotNull(message = VALIDATION_CITY_STATE_IS_REQUIRED)
    StateDto state;

}
