package com.findservices.serviceprovider.user.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.ApiError;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.login.model.RoleType;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import com.findservices.serviceprovider.user.model.RegisterUserDtoInput;
import com.findservices.serviceprovider.user.model.RegisterUserDtoOutput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserDtoInput createUser(RegisterUserDtoInput registerUserDtoInput) {
        UserEntity userEntity = mapper.map(registerUserDtoInput, UserEntity.class);
        userEntity.setAddress(new ArrayList<>());

        String encryptedPassword = passwordEncoder.encode(registerUserDtoInput.getPassword());

        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser(userEntity);
        loginEntity.setPassword(encryptedPassword);
        loginEntity.setUsername(registerUserDtoInput.getLogin());

        userEntity.setLogin(loginEntity);

        userEntity = userRepository.saveAndFlush(userEntity);
        registerUserDtoInput.setId(userEntity.getId());
        return registerUserDtoInput;
    }

    public ServiceProviderEntity createServiceProvider(ServiceProviderEntity serviceProviderEntity) {
        UserEntity user = this.userRepository.findById(serviceProviderEntity.getUser().getId()) //
                .orElseThrow(() -> new ConstraintViolationException(null, null, TranslationConstants.FK_SERVICE_PROVIDER_USER_ID));

        serviceProviderEntity.setId(user.getId());
        userRepository.saveAndFlush(user);
        return serviceProviderEntity;
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
    public RegisterUserDtoOutput updateUser(RegisterUserDtoInput user) {
        UserEntity currentUser = this.getCurrentUser();
        currentUser.setLastName(user.getLastName());
        currentUser.setName(user.getName());
        currentUser.setUserPhotoUrl(user.getUserPhotoUrl());
        currentUser.setCpf(user.getCpf());
        currentUser.setCpf(user.getLogin());
        userRepository.save(currentUser);
        return mapper.map(user, RegisterUserDtoOutput.class);
    }

    public UserEntity getCurrentUser() {
        LoginEntity login = (LoginEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(login.getId()).get();
    }

    public UserDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    final UserDto userDto = new UserDto();
                    userDto.setUserPhotoUrl(userEntity.getUserPhotoUrl());
                    userDto.setName(userEntity.getName());
                    userDto.setLastName(userEntity.getLastName());
                    userDto.setId(userEntity.getId());
                    return userDto;
                }).orElse(null);
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

    public void toServiceProvider(UserEntity currentUser) {
        currentUser.getLogin().setRole(RoleType.SERVICE_PROVIDER);
        this.userRepository.save(currentUser);
    }
}
