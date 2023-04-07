package com.mei.serviceprovider.country.model;

import org.springframework.stereotype.Component;

@Component
public class CountryDtoConverter {

    public CountryDto toDto(CountryEntity entity) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(entity.getId());
        countryDto.setName(entity.getName());
        return countryDto;
    }

}
