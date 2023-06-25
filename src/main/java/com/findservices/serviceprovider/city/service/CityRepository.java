package com.findservices.serviceprovider.city.service;

import com.findservices.serviceprovider.city.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

interface CityRepository extends JpaRepository<CityEntity, UUID> {

    Optional<CityEntity> findByNameIgnoreCase(String name);

    List<CityEntity> findAllByNameInIgnoreCase(Set<String> names);

}
