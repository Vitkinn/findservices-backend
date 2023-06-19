package com.findservices.serviceprovider.profileevaluation.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ProfileEvaluationOutput {

    Integer rate;

    Integer quantity;

    List<ProfileEvaluationViewDto> evaluations;

}
