package com.mei.serviceprovider.city.model;

import com.mei.serviceprovider.state.model.StateDtoConverter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityDtoConverter {

    StateDtoConverter stateDtoConverter;

    public CityDto toDto(CityEntity entity) {
        CityDto cityDto = new CityDto();
        cityDto.setId(entity.getId());
        cityDto.setName(entity.getName());
        cityDto.setState(stateDtoConverter.toDto(entity.getState()));
        return cityDto;
    }

}
