package com.findservices.serviceprovider.city.service;

import com.findservices.serviceprovider.city.model.CityDto;
import com.findservices.serviceprovider.city.model.CityEntity;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
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
public class CityService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MessageSource messageSource;

    public CityDto createCity(CityDto cityDto) {
        CityEntity cityEntity = modelMapper.map(cityDto, CityEntity.class);
        cityEntity = cityRepository.saveAndFlush(cityEntity);
        cityDto.setId(cityEntity.getId());
        return cityDto;
    }

    public List<CityDto> list() {
        return cityRepository.findAll().stream() //
                .map(entity -> modelMapper.map(entity, CityDto.class)) //
                .collect(Collectors.toList());
    }

    public CityDto findById(UUID id) {
        return cityRepository.findById(id) //
                .map(entity -> modelMapper.map(entity, CityDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    public CityDto updateCity(UUID id, CityDto city) {
        if (!cityRepository.existsById(id)) {
            throw notFoundError();
        } else {
            city.setId(id);
            CityEntity entity = modelMapper.map(city, CityEntity.class);
            cityRepository.saveAndFlush(entity);
            return city;
        }
    }

    private HandleException notFoundError() {
        return new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_CITY_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }
}
