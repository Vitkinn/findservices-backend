package com.mei.serviceprovider.state.service;

import com.mei.serviceprovider.country.model.CountryEntity;
import com.mei.serviceprovider.state.model.StateDto;
import com.mei.serviceprovider.state.model.StateDtoConverter;
import com.mei.serviceprovider.state.model.StateEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateService {

    StateRepository stateRepository;

    StateDtoConverter stateDtoConverter;

    @Transactional
    public StateDto createState(StateDto stateDto) {
        StateEntity stateEntity = new StateEntity();
        stateEntity.setName(stateDto.getName());
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(stateDto.getCountry().getId());
        stateEntity.setCountry(countryEntity);
        stateEntity = stateRepository.saveAndFlush(stateEntity);
        stateDto.setId(stateEntity.getId());
        return stateDto;
    }

    public List<StateDto> list() {
        return stateRepository.findAll().stream()
                .map(stateDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public StateDto findById(UUID id) {
        return stateRepository.findById(id)
                .map(stateDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }

    public StateDto updateState(UUID id, StateDto state) {
        return stateRepository.findById(id)
                .map(entity -> {
                    entity.setName(state.getName());
                    return stateRepository.saveAndFlush(entity);
                })
                .map(stateDtoConverter::toDto)
                .orElseThrow(() -> new ServiceException("not found"));
    }
}
