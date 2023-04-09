package com.mei.serviceprovider.city.model;

import com.mei.serviceprovider.state.model.StateDto;
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
public class CityDto {

    UUID id;

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

    @NotNull
    StateDto state;

}
