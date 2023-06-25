package com.findservices.serviceprovider.serviceprovider.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.serviceprovider.model.*;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.service.FirebaseService;
import lombok.AccessLevel;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceProviderService {

    @Autowired
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    MessageSource messageSource;
    @Autowired
    FirebaseService firebaseService;

//    @Transactional
//    public ServiceProviderDto createServiceProvider(ServiceProviderDto serviceProviderDto) {
//        ServiceProviderEntity serviceProviderEntity = mapper.map(serviceProviderDto, ServiceProviderEntity.class);
//        UserEntity currentUser = userService.getCurrentUser();
//        serviceProviderEntity.setId(currentUser.getId());
//        serviceProviderEntity.setActuationCities(cityService.findAllByNames(serviceProviderDto.getActuationCities()));
//
//        serviceProviderEntity = serviceProviderRepository.saveAndFlush(serviceProviderEntity);
//        serviceProviderDto.setId(serviceProviderEntity.getId());
//
//
//        userService.toServiceProvider(currentUser);
//        return serviceProviderDto;
//    }

    public List<ServiceProviderDto> list() {
        return serviceProviderRepository.findAll().stream() //
                .map(entity -> mapper.map(entity, ServiceProviderDto.class)) //
                .collect(Collectors.toList());
    }

    public ServiceProviderDto findById(UUID id) {
        return serviceProviderRepository.findById(id) //
                .map(entity -> mapper.map(entity, ServiceProviderDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    @Transactional
    public ServiceProviderDto updateServiceProvider(UUID id, ServiceProviderDto serviceProvider) {
        if (!serviceProviderRepository.existsById(id)) {
            throw notFoundError();
        } else {
            serviceProvider.setId(id);
            ServiceProviderEntity entity = mapper.map(serviceProvider, ServiceProviderEntity.class);
            serviceProviderRepository.saveAndFlush(entity);
            return serviceProvider;
        }
    }

    public List<ServiceProviderListDto> findByFilters(ServiceProviderFilterDto serviceProviderFilterDto) {
        String like = "%";
        if (serviceProviderFilterDto.name != null) {
            like = like.concat(serviceProviderFilterDto.name).concat("%").toUpperCase();
        }
        List<ServiceProviderListTuple> serviceProviders;
        if (serviceProviderFilterDto.category == null && serviceProviderFilterDto.city == null) {
            serviceProviders = serviceProviderRepository.findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCase(like);
        } else if (serviceProviderFilterDto.category != null && serviceProviderFilterDto.city != null) {
            serviceProviders = serviceProviderRepository.findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndCategoryAndActuationCitiesName( //
                            like, serviceProviderFilterDto.category, serviceProviderFilterDto.city);
        } else if (serviceProviderFilterDto.category != null) {
            serviceProviders = serviceProviderRepository.findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndCategory( //
                            like, serviceProviderFilterDto.category);
        } else {
            serviceProviders = serviceProviderRepository.findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndActuationCitiesName( //
                            like, serviceProviderFilterDto.city);
        }
        return serviceProviders
                .stream() //
                .map(serviceProvider -> {
                    UserDto userDto = new UserDto();
                    userDto.setPhotoUrl(serviceProvider.getPhoto() != null ? firebaseService.getImageUrl(serviceProvider.getPhoto()) : null);
                    userDto.setName(serviceProvider.getName());
                    userDto.setLastName(serviceProvider.getLastName());

                    ServiceProviderListDto serviceProviderListDto = new ServiceProviderListDto();
                    serviceProviderListDto.setUser(userDto);
                    serviceProviderListDto.setId(serviceProvider.getId());
                    serviceProviderListDto.setDescription(serviceProvider.getDescription());
                    serviceProviderListDto.setCategoryType(serviceProvider.getCategory());
                    return serviceProviderListDto;
                }) //
                .collect(Collectors.toList());

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

    public ServiceProviderEntity save(ServiceProviderEntity serviceProviderEntity) {
        return serviceProviderRepository.saveAndFlush(serviceProviderEntity);
    }
}
