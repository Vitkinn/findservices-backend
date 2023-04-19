package com.findservices.serviceprovider.city.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.state.model.StateDto;
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
public class CityDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

    @NotNull
    StateDto state;

}
