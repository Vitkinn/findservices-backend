package com.mei.serviceprovider.state.service;

import com.mei.serviceprovider.state.model.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StateRepository extends JpaRepository<StateEntity, UUID> {
}
