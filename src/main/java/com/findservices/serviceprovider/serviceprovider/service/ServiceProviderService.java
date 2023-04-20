package com.findservices.serviceprovider.serviceprovider.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderDto;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
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
public class ServiceProviderService {

    ServiceProviderRepository serviceProviderRepository;

    ModelMapper mapper;

    MessageSource messageSource;

    @Transactional
    public ServiceProviderDto createServiceProvider(ServiceProviderDto serviceProviderDto) {
        ServiceProviderEntity serviceProviderEntity = mapper.map(serviceProviderDto, ServiceProviderEntity.class);
        serviceProviderEntity = serviceProviderRepository.saveAndFlush(serviceProviderEntity);
        serviceProviderDto.setId(serviceProviderEntity.getId());
        return serviceProviderDto;
    }

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
