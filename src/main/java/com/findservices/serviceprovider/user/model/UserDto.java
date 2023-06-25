package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.serviceprovider.model.CategoryType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    UUID id;

    @NotNull
    String name;

    @NotNull
    String lastName;

    String photoUrl;

    LocalDate createAccountDate;

    String description;

    CategoryType category;

    Set<String> actuationCities;

}
