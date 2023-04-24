package com.findservices.serviceprovider.common.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class IdDto {

    @NotNull
    UUID id;
}
