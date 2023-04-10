package com.findservices.serviceprovider.state.service;

import com.findservices.serviceprovider.state.model.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, UUID> {
}
