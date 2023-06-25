package com.findservices.serviceprovider.profileevaluation.service;

import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationRateDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationEntity;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationViewDto;
import com.findservices.serviceprovider.user.service.FirebaseService;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEvaluationService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProfileEvaluationRepository repository;
    @Autowired
    UserService userService;
    @Autowired
    FirebaseService firebaseService;

    @Transactional
    public ProfileEvaluationRateDto evaluateUser(ProfileEvaluationRateDto profileEvaluationDto) {
        ProfileEvaluationEntity profileEvaluationEntity = new ProfileEvaluationEntity();

        profileEvaluationEntity.setComment(profileEvaluationDto.getComment());
        profileEvaluationEntity.setRate(profileEvaluationDto.getScore());
        profileEvaluationEntity.setEvaluationDate(LocalDate.now());

        profileEvaluationEntity.setRatedUser(userService.findEntityById(profileEvaluationDto.getRatedUser().getId()) //
                .orElse(null));

        profileEvaluationEntity.setEvaluatorUser(userService.getCurrentUser());

        profileEvaluationEntity = repository.save(profileEvaluationEntity);
        profileEvaluationDto.setId(profileEvaluationEntity.getId());
        return profileEvaluationDto;
    }

    public int calculateScore(List<ProfileEvaluationViewDto> profileEvaluations) {
        final int score;
        if (profileEvaluations.isEmpty()) {
            score = 0;
        } else {
            int sumAllScores = profileEvaluations.stream().flatMapToInt(evaluation -> IntStream.of(evaluation.getRate().intValue())).sum();
            score = sumAllScores / profileEvaluations.size();
        }
        return score;
    }

    public List<ProfileEvaluationViewDto> listByUser(UUID userId) {
        return repository.findAllByRatedUserIdOrderByEvaluationDateDesc(userId).stream() //
                .map(entity -> modelMapper.map(entity, ProfileEvaluationViewDto.class)) //
                .peek(user -> {
                    String imageUrl = firebaseService.getImageUrl(user.getEvaluatorUser().getPhotoUrl());
                    user.getEvaluatorUser().setPhotoUrl(imageUrl);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID evaluationId) {
        repository.deleteById(evaluationId);
    }

}
