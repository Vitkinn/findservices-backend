package com.findservices.serviceprovider.user.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserDto extends BaseDto {

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
    @Size(max = 11, message = "max")
    @Size(min = 11, message = "min")
    String cpf;

    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).{8,}")
    String password;

    @NotNull
    String login;

}
