package com.findservices.serviceprovider.country.service;

import com.findservices.serviceprovider.country.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
}
