package com.findservices.serviceprovider.state.service;

import com.findservices.serviceprovider.state.model.StateDto;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.state.model.StateEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateService {

    StateRepository stateRepository;

    ModelMapper mapper;

    MessageSource messageSource;

    @Transactional
    public StateDto createState(StateDto stateDto) {
        StateEntity stateEntity = mapper.map(stateDto, StateEntity.class);
        stateEntity = stateRepository.saveAndFlush(stateEntity);
        stateDto.setId(stateEntity.getId());
        return stateDto;
    }

    public List<StateDto> list() {
        return stateRepository.findAll().stream() //
                .map(entity -> mapper.map(entity, StateDto.class)) //
                .collect(Collectors.toList());
    }

    public StateDto findById(UUID id) {
        return stateRepository.findById(id) //
                .map(entity -> mapper.map(entity, StateDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    @Transactional
    public StateDto updateState(UUID id, StateDto state) {
        return stateRepository.findById(id)
                .map(entity -> { //
                    mapper.map(state, entity);
                    entity.setId(id);
                    return stateRepository.saveAndFlush(entity);
                }) //
                .map(entity -> mapper.map(entity, StateDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    private HandleException notFoundError() {
        return new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_STATE_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }
}
