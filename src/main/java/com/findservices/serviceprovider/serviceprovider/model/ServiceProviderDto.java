package com.findservices.serviceprovider.serviceprovider.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.common.model.IdDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceProviderDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Size(max = 14, message = "max")
    @Size(min = 14, message = "min")
    String cnpj;

    @NotEmpty
    @NotNull
    List<IdDto> actuationCities;

    @NotNull
    IdDto user;
}
