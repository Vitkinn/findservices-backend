package com.findservices.serviceprovider.country.service;


import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.country.model.CountryDto;
import com.findservices.serviceprovider.country.model.CountryEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MessageSource messageSource;

    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity countryEntity = modelMapper.map(countryDto, CountryEntity.class);
        countryEntity = countryRepository.saveAndFlush(countryEntity);
        countryDto.setId(countryEntity.getId());
        return countryDto;
    }

    public List<CountryDto> list() {
        return countryRepository.findAll().stream() //
                .map(entity -> modelMapper.map(entity, CountryDto.class)) //
                .collect(Collectors.toList());
    }

    public CountryDto findById(UUID id) {
        return countryRepository.findById(id) //
                .map(entity -> modelMapper.map(entity, CountryDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    public CountryDto updateCountry(UUID id, CountryDto country) {
        if (!countryRepository.existsById(id)) {
            throw notFoundError();
        } else {
            country.setId(id);
            CountryEntity entity = modelMapper.map(country, CountryEntity.class);
            countryRepository.saveAndFlush(entity);
            return country;
        }
    }

    private HandleException notFoundError() {
        return new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_COUNTRY_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }
}
