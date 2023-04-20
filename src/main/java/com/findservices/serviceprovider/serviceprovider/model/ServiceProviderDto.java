package com.findservices.serviceprovider.serviceprovider.model;

import com.findservices.serviceprovider.address.model.AddressDto;
import com.findservices.serviceprovider.common.model.BaseDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceProviderDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Size(max = 150)
    String name;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    String lastName;

    @Size(max = 255)
    String userPhotoUrl;

    @NotNull
    @NotEmpty
    @Size(max = 11, min = 11)
    String cpf;

    AddressDto address;

}
