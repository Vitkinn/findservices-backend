package com.mei.serviceprovider.city.service;

import com.mei.serviceprovider.city.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CityRepository extends JpaRepository<CityEntity, UUID> {
}
