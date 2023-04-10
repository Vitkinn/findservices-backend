package com.findservices.serviceprovider.address.service;

import com.findservices.serviceprovider.address.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
