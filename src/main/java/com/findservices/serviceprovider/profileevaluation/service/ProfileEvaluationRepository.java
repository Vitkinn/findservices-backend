package com.findservices.serviceprovider.profileevaluation.service;

import com.findservices.serviceprovider.profileevaluation.model.ProfileEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileEvaluationRepository extends JpaRepository<ProfileEvaluationEntity, UUID> {

    List<ProfileEvaluationEntity> findAllByRatedUserIdOrderByEvaluationDateDesc(UUID ratedUserId);

}
