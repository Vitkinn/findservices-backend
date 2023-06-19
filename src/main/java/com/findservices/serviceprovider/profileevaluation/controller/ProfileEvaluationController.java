package com.findservices.serviceprovider.profileevaluation.controller;

import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationRateDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationOutput;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationViewDto;
import com.findservices.serviceprovider.profileevaluation.service.ProfileEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profileEvaluation")
public class ProfileEvaluationController {

    @Autowired
    private ProfileEvaluationService profileEvaluationService;

    @PostMapping(value = "")
    public ProfileEvaluationRateDto create(@RequestBody @Valid ProfileEvaluationRateDto profileEvaluationDto) {
        return this.profileEvaluationService.evaluateUser(profileEvaluationDto);
    }

    @GetMapping(value = "/{userId}")
    public ProfileEvaluationOutput listByUserId(@PathVariable @Valid UUID userId) {
        List<ProfileEvaluationViewDto> profileEvaluations = this.profileEvaluationService.listByUser(userId);
        final int score = this.profileEvaluationService.calculateScore(profileEvaluations);
        return ProfileEvaluationOutput.builder()
                .rate(score)
                .quantity(profileEvaluations.size())
                .evaluations(profileEvaluations)
                .build();
    }

}
