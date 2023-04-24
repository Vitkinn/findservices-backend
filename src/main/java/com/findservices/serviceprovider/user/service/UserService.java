package com.findservices.serviceprovider.user.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    ModelMapper mapper;

    MessageSource messageSource;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setAddress(new ArrayList<>());
        userEntity = userRepository.saveAndFlush(userEntity);
        userDto.setId(userEntity.getId());
        return userDto;
    }

    public List<UserDto> list() {
        return userRepository.findAll().stream() //
                .map(entity -> mapper.map(entity, UserDto.class)) //
                .collect(Collectors.toList());
    }

    public UserDto findById(UUID id) {
        return userRepository.findById(id) //
                .map(entity -> mapper.map(entity, UserDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    public Optional<UserEntity> findEntityById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserDto user) {
        if (!userRepository.existsById(id)) {
            throw notFoundError();
        } else {
            user.setId(id);
            UserEntity entity = mapper.map(user, UserEntity.class);
            userRepository.saveAndFlush(entity);
            return user;
        }
    }

    private HandleException notFoundError() {
        return new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_USER_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }
}
