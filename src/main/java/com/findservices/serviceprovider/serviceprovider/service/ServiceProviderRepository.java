package com.findservices.serviceprovider.serviceprovider.service;

import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderEntity, UUID> {
    List<ServiceProviderEntity> findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCase(String name, String lastName);
}
