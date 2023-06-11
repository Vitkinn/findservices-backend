package com.findservices.serviceprovider.profileevaluation.service;

import com.findservices.serviceprovider.address.model.AddressDto;
import com.findservices.serviceprovider.address.model.AddressEntity;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationEntity;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileEvaluationService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProfileEvaluationRepository repository;
    @Autowired
    UserService userService;

    @Transactional
    public ProfileEvaluationDto evaluateUser(ProfileEvaluationDto profileEvaluationDto) {
        ProfileEvaluationEntity profileEvaluationEntity = new ProfileEvaluationEntity();

        profileEvaluationEntity.setComment(profileEvaluationDto.getComment());
        profileEvaluationEntity.setScore(profileEvaluationDto.getScore().shortValue());

        profileEvaluationEntity.setRatedUser(userService.findEntityById(profileEvaluationDto.getRatedUser().getId()) //
                .orElse(null));

        profileEvaluationEntity.setEvaluatorUser(userService.getCurrentUser());

        profileEvaluationEntity = repository.save(profileEvaluationEntity);
        profileEvaluationDto.setId(profileEvaluationEntity.getId());
        return profileEvaluationDto;
    }

    public List<ProfileEvaluationDto> listByUser(UUID userId) {
        return repository.findAllByRatedUserId(userId).stream() //
                .map(entity -> modelMapper.map(entity, ProfileEvaluationDto.class)) //
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID evaluationId) {
        repository.deleteById(evaluationId);
    }

}
