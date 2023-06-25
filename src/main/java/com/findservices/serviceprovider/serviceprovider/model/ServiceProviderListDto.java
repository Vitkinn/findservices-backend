package com.findservices.serviceprovider.serviceprovider.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.common.model.IdDto;
import com.findservices.serviceprovider.user.model.UserDto;
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
public class ServiceProviderListDto extends BaseDto {

    String description;

    CategoryType categoryType;

    UserDto user;
}
