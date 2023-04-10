package com.findservices.serviceprovider.city.service;

import com.findservices.serviceprovider.city.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CityRepository extends JpaRepository<CityEntity, UUID> {
}
