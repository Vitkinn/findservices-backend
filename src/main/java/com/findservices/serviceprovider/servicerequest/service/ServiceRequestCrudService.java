package com.findservices.serviceprovider.servicerequest.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.servicerequest.model.*;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Setter(onMethod_ = @Autowired)
public class ServiceRequestCrudService {

    UserService userService;
    MessageSource messageSource;
    ServiceRequestRepository serviceRequestRepository;
    ModelMapper mapper;

    @Transactional
    public ServiceRequestDto createServiceRequest(ClientServiceRequestDto clientServiceRequestDto) {

        UserEntity userServiceProvider = userService.findEntityById(clientServiceRequestDto.getServiceProvider().getId()) //
                .filter(userEntity -> userEntity.getServiceProvider() != null) //
                .orElseThrow(serviceProviderNotFound());

        ServiceRequestEntity serviceRequest = mapper.map(clientServiceRequestDto, ServiceRequestEntity.class);

        serviceRequest.setServiceProvider(userServiceProvider);
        serviceRequest.setServiceRequester(userService.getCurrentUser());

        serviceRequest = serviceRequestRepository.saveAndFlush(serviceRequest);

        return mapper.map(serviceRequest, ServiceRequestDto.class);
    }

    @Transactional
    public ServiceRequestDto evaluateRequest(UUID id, EvaluateRequestDto evaluateRequestDto) {

        ServiceRequestEntity serviceRequest = serviceRequestRepository.findById(id).orElseThrow(serviceRequestNotFound());

        if (serviceRequest.getServiceRequester() != userService.getCurrentUser()) {
            throw this.forbidden();
        }

        serviceRequest.setRequestStatus(RequestStatusType.PENDING_CLIENT_APPROVED);
        serviceRequest.setValue(evaluateRequestDto.getValue());
        serviceRequest.setValueJustification(evaluateRequestDto.getValueJustification());

        serviceRequest = serviceRequestRepository.saveAndFlush(serviceRequest);

        return mapper.map(serviceRequest, ServiceRequestDto.class);
    }

    private Supplier<HandleException> serviceProviderNotFound() {
        return () -> new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_SERVICE_PROVIDER_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }

    private Supplier<HandleException> serviceRequestNotFound() {
        return () -> new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_SERVICE_REQUEST_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }

    private HandleException forbidden() {
        return new HandleException( //
                messageSource.getMessage( //
                        "forbidden",
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.FORBIDDEN //
        );
    }

}
