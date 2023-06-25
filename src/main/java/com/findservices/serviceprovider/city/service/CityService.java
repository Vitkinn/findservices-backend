package com.findservices.serviceprovider.city.service;

import com.findservices.serviceprovider.city.model.CityEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public CityEntity findByName(String name) {
        return cityRepository.findByNameIgnoreCase(name)
                .orElse(new CityEntity());
    }

    public List<CityEntity> findAllByNames(Set<String> names) {
        final List<CityEntity> allByNameInIgnoreCase = cityRepository.findAllByNameInIgnoreCase(names);
        List<CityEntity> cities = names.stream().map(name -> allByNameInIgnoreCase.stream()
                .filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst().orElseGet(() -> {
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setName(name);
                    return cityEntity;
                })).collect(Collectors.toList());

        return cityRepository.saveAll(cities);
    }
}
