package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.serviceprovider.model.CategoryType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDto {

    UUID id;

    String name;

    String lastName;

    String userPhotoUrl;

    String cpf;

    String login;

    String phone;

    String cnpj;
    String description;
    Set<String> actuationCities;
    CategoryType category;
}
