package com.findservices.serviceprovider.user.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import com.findservices.serviceprovider.user.model.RegisterUserDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserDto createUser(RegisterUserDto registerUserDto) {
        UserEntity userEntity = mapper.map(registerUserDto, UserEntity.class);
        userEntity.setAddress(new ArrayList<>());

        String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());

        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser(userEntity);
        loginEntity.setPassword(encryptedPassword);
        loginEntity.setUsername(registerUserDto.getLogin());

        userEntity.setLogin(loginEntity);

        userEntity = userRepository.saveAndFlush(userEntity);
        registerUserDto.setId(userEntity.getId());
        return registerUserDto;
    }

    public ServiceProviderEntity createServiceProvider(ServiceProviderEntity serviceProviderEntity) {
        UserEntity user = this.userRepository.findById(serviceProviderEntity.getUser().getId()) //
                .orElseThrow(() -> new ConstraintViolationException(null, null, TranslationConstants.FK_SERVICE_PROVIDER_USER_ID));

        serviceProviderEntity.setId(user.getId());
        userRepository.saveAndFlush(user);
        return serviceProviderEntity;
    }

    public List<RegisterUserDto> list() {
        return userRepository.findAll().stream() //
                .map(entity -> mapper.map(entity, RegisterUserDto.class)) //
                .collect(Collectors.toList());
    }

    public RegisterUserDto findById(UUID id) {
        return userRepository.findById(id) //
                .map(entity -> mapper.map(entity, RegisterUserDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    public Optional<UserEntity> findEntityById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public RegisterUserDto updateUser(UUID id, RegisterUserDto user) {
        if (!userRepository.existsById(id)) {
            throw notFoundError();
        } else {
            user.setId(id);
            UserEntity entity = mapper.map(user, UserEntity.class);
            userRepository.saveAndFlush(entity);
            return user;
        }
    }

    public UserEntity getCurrentUser() {
        LoginEntity login = (LoginEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(login.getId()).get();
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
