package com.mei.serviceprovider.state.model;

import com.mei.serviceprovider.country.model.CountryDtoConverter;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateDtoConverter {

    CountryDtoConverter countryDtoConverter;

    public StateDto toDto(StateEntity entity) {
        StateDto stateDto = new StateDto();
        stateDto.setId(entity.getId());
        stateDto.setName(entity.getName());
        stateDto.setCountry(countryDtoConverter.toDto(entity.getCountry()));
        return stateDto;
    }

}
