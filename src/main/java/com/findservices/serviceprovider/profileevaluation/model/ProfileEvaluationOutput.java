package com.findservices.serviceprovider.profileevaluation.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProfileEvaluationOutput {

    Double score;

    List<ProfileEvaluationDto> evaluations;

}
