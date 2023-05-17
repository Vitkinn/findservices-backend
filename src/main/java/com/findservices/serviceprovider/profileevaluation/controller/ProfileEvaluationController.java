package com.findservices.serviceprovider.profileevaluation.controller;

import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationOutput;
import com.findservices.serviceprovider.profileevaluation.service.ProfileEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/profileEvaluation")
public class ProfileEvaluationController {

    @Autowired
    private ProfileEvaluationService profileEvaluationService;

    @PostMapping(value = "")
    public ProfileEvaluationDto create(@RequestBody @Valid ProfileEvaluationDto profileEvaluationDto) {
        return this.profileEvaluationService.evaluateUser(profileEvaluationDto);
    }

    @GetMapping(value = "/{userId}")
    public ProfileEvaluationOutput listByUserId(@PathVariable @Valid UUID userId) {
        List<ProfileEvaluationDto> profileEvaluations = this.profileEvaluationService.listByUser(userId);
        long sumAllScores = profileEvaluations.stream().flatMapToLong(evaluation -> LongStream.of(evaluation.getScore())).sum();
        return ProfileEvaluationOutput.builder()
                .score((double) sumAllScores / profileEvaluations.size())
                .evaluations(profileEvaluations)
                .build();
    }

}
