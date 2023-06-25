package com.findservices.serviceprovider.user.service;

import com.findservices.serviceprovider.city.model.CityEntity;
import com.findservices.serviceprovider.city.service.CityService;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.login.model.RoleType;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import com.findservices.serviceprovider.serviceprovider.service.ServiceProviderService;
import com.findservices.serviceprovider.user.model.RegisterUserDtoInput;
import com.findservices.serviceprovider.user.model.UpdateUserDto;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    FirebaseService firebaseService;

    @Autowired
    ServiceProviderService serviceProviderService;

    @Autowired
    CityService cityService;

    @Transactional
    public RegisterUserDtoInput createUser(RegisterUserDtoInput registerUserDtoInput) {
        UserEntity userEntity = mapper.map(registerUserDtoInput, UserEntity.class);
        userEntity.setAddress(new ArrayList<>());
        userEntity.setCreateAccountDate(LocalDate.now());

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
    public UpdateUserDto updateUser(UpdateUserDto user) {
        UserEntity currentUser = this.getCurrentUser();
        if (user.getCnpj() != null) {
            ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
            serviceProviderEntity.setCategory(user.getCategory());
            serviceProviderEntity.setCnpj(user.getCnpj());
            serviceProviderEntity.setDescription(user.getDescription());
            serviceProviderEntity.setActuationCities(cityService.findAllByNames(user.getActuationCities()));
            serviceProviderEntity.setUser(currentUser);
            serviceProviderEntity.setId(currentUser.getId());
            serviceProviderEntity = serviceProviderService.save(serviceProviderEntity);
            currentUser.setServiceProvider(serviceProviderEntity);
            currentUser.getLogin().setRole(RoleType.SERVICE_PROVIDER);
        }

        if (user.getUserPhotoUrl() != currentUser.getUserPhotoUrl()) {
            firebaseService.deleteFile(currentUser.getUserPhotoUrl());
        }

        currentUser.setLastName(user.getLastName());
        currentUser.setName(user.getName());
        currentUser.setUserPhotoUrl(user.getUserPhotoUrl());
        currentUser.setCpf(user.getCpf());
        currentUser.getLogin().setUsername(user.getLogin());
        userRepository.save(currentUser);
        return mapper.map(user, UpdateUserDto.class);
    }

    public UserEntity getCurrentUser() {
        LoginEntity login = (LoginEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(login.getId()).get();
    }

    public UserDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    final UserDto userDto = new UserDto();
                    if (userEntity.getUserPhotoUrl() != null) {
                        final String imageUrl = firebaseService.getImageUrl(userEntity.getUserPhotoUrl());
                        userDto.setPhotoUrl(imageUrl);
                    }

                    userDto.setName(userEntity.getName());
                    userDto.setLastName(userEntity.getLastName());
                    userDto.setCreateAccountDate(userEntity.getCreateAccountDate());
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

    public UpdateUserDto getCurrentUserModel() {
        final UserEntity userEntity = this.getCurrentUser();
        final UpdateUserDto editUserDto = new UpdateUserDto();
        editUserDto.setPhone(userEntity.getPhone());
        editUserDto.setName(userEntity.getName());
        editUserDto.setLastName(userEntity.getLastName());
        editUserDto.setLogin(userEntity.getLogin().getUsername());

        if (userEntity.getUserPhotoUrl() != null) {
            final String imageUrl = firebaseService.getImageUrl(userEntity.getUserPhotoUrl());
            editUserDto.setUserPhotoUrl(imageUrl);
        }

        ServiceProviderEntity serviceProvider = userEntity.getServiceProvider();
        if (serviceProvider != null) {
            editUserDto.setCnpj(serviceProvider.getCnpj());
            editUserDto.setDescription(serviceProvider.getDescription());
            editUserDto.setCategory(serviceProvider.getCategory());
            editUserDto.setActuationCities(serviceProvider.getActuationCities().stream().map(CityEntity::getName).collect(Collectors.toSet()));
        }

        editUserDto.setId(userEntity.getId());
        editUserDto.setCpf(userEntity.getCpf());
        return editUserDto;
    }
}
