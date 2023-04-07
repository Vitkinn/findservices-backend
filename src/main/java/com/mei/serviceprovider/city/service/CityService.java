package com.mei.serviceprovider.city.service;

import com.mei.serviceprovider.city.model.CityDto;
import com.mei.serviceprovider.city.model.CityDtoConverter;
import com.mei.serviceprovider.city.model.CityEntity;
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
public class CityService {

    CityRepository cityRepository;

    CityDtoConverter cityDtoConverter;

    public CityDto createCity(CityDto cityDto) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(cityDto.getName());
        cityEntity = cityRepository.saveAndFlush(cityEntity);
        cityDto.setId(cityEntity.getId());
        return cityDto;
    }

    public List<CityDto> list() {
        return cityRepository.findAll().stream()
                .map(cityDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public CityDto findById(UUID id) {
        return cityRepository.findById(id)
                .map(cityDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }

    public CityDto updateCity(UUID id, CityDto city) {
        return cityRepository.findById(id)
                .map(entity -> {
                    entity.setName(city.getName());
                    return cityRepository.saveAndFlush(entity);
                })
                .map(cityDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }
}
