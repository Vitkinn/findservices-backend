package com.findservices.serviceprovider.servicerequest.model;

import com.findservices.serviceprovider.common.model.IdDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientServiceRequestDto {

    UUID id;

    @NotNull
    String serviceDescription;

    Double clientWishValue;

    @NotNull @Valid
    IdDto serviceProvider;
}
