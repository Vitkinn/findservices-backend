package com.mei.serviceprovider.country.service;


import com.mei.serviceprovider.country.model.CountryDto;
import com.mei.serviceprovider.country.model.CountryDtoConverter;
import com.mei.serviceprovider.country.model.CountryEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryService {

    CountryRepository countryRepository;
    CountryDtoConverter countryDtoConverter;

    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(countryDto.getName());
        countryEntity = countryRepository.saveAndFlush(countryEntity);
        countryDto.setId(countryEntity.getId());
        return countryDto;
    }

    public List<CountryDto> list() {
        return countryRepository.findAll().stream()
                .map(countryDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public CountryDto findById(UUID id) {
        return countryRepository.findById(id)
                .map(countryDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }

    public CountryDto updateCountry(UUID id, CountryDto country) {
        return countryRepository.findById(id)
                .map(entity -> {
                    entity.setName(country.getName());
                    return countryRepository.saveAndFlush(entity);
                })
                .map(countryDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }
}
