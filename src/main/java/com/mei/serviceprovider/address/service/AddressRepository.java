package com.mei.serviceprovider.address.service;

import com.mei.serviceprovider.address.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
