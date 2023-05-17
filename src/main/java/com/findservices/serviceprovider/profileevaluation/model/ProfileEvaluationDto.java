package com.findservices.serviceprovider.profileevaluation.model;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.common.model.IdDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEvaluationDto extends BaseDto {

    String comment;

    @Max(5)
    @Min(0)
    @NotNull
    Long score;

    @NotNull
    @Valid
    BaseDto ratedUser;

}
