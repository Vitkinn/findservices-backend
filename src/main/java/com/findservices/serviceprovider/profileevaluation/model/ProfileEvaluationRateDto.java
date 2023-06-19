package com.findservices.serviceprovider.profileevaluation.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.user.model.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEvaluationRateDto extends BaseDto {

    String comment;

    @Max(5)
    @Min(0)
    @NotNull
    Short score;

    @NotNull
    @Valid
    BaseDto ratedUser;

}
