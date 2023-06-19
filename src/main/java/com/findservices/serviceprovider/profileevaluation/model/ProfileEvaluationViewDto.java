package com.findservices.serviceprovider.profileevaluation.model;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.user.model.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEvaluationViewDto extends BaseDto {

    String comment;
    Short rate;
    UserDto evaluatorUser;
    LocalDate evaluationDate;

}
