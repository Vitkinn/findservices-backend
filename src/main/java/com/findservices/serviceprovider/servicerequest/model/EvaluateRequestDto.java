package com.findservices.serviceprovider.servicerequest.model;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluateRequestDto {

    @NotNull
    Double value;

    String valueJustification;

}
