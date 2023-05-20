package com.findservices.serviceprovider.servicerequest.service;

import com.findservices.serviceprovider.servicerequest.model.ServiceRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, UUID> {
}
