package com.findservices.serviceprovider.profileevaluation.service;

import com.findservices.serviceprovider.common.model.BaseDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationRateDto;
import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationEntity;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ProfileEvaluationServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private ProfileEvaluationRepository repository;

    @InjectMocks
    private ProfileEvaluationService profileEvaluationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testEvaluateUser() {
        // Given
        ProfileEvaluationRateDto profileEvaluationDto = new ProfileEvaluationRateDto();
        String testComment = "Test comment";
        profileEvaluationDto.setComment(testComment);
        profileEvaluationDto.setScore(5L);
        BaseDto ratedUser = new BaseDto();
        UUID userId = UUID.randomUUID();
        ratedUser.setId(userId);
        profileEvaluationDto.setRatedUser(ratedUser);
        UUID id = UUID.randomUUID();

        ProfileEvaluationEntity savedEntity = new ProfileEvaluationEntity();
        savedEntity.setId(id);

        UserEntity currentUser = new UserEntity();
        UserEntity ratedUserEntity = new UserEntity();

        ProfileEvaluationEntity entityToCreate = new ProfileEvaluationEntity();
        entityToCreate.setEvaluatorUser(currentUser);
        entityToCreate.setRatedUser(ratedUserEntity);
        entityToCreate.setRate((short) 5);
        entityToCreate.setComment(testComment);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(userService.findEntityById(userId)).thenReturn(Optional.of(ratedUserEntity));
        when(repository.save(refEq(entityToCreate))).thenReturn(savedEntity);

        // When
        ProfileEvaluationRateDto result = profileEvaluationService.evaluateUser(profileEvaluationDto);

        // Then
        Assertions.assertEquals(id, result.getId());
        verify(userService).getCurrentUser();
        verify(userService).findEntityById(userId);
        verify(repository).save(refEq(entityToCreate));
        verifyNoMoreInteractions(repository, userService);
    }
}