package com.findservices.serviceprovider.servicerequest.service;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.translate.TranslationService;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.servicerequest.model.*;
import com.findservices.serviceprovider.servicerequest.model.RequestStatusType;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Supplier;

import static com.findservices.serviceprovider.common.constants.TranslationConstants.ERROR_SERVICE_PROVIDER_NOT_FOUND;

@Service
@Setter(onMethod_ = @Autowired)
public class ServiceRequestCrudService {

    UserService userService;
    TranslationService translationService;
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

        validateClientOwner(serviceRequest);

        serviceRequest.setRequestStatus(RequestStatusType.PENDING_CLIENT_APPROVED);
        serviceRequest.setValue(evaluateRequestDto.getValue());
        serviceRequest.setValueJustification(evaluateRequestDto.getValueJustification());

        serviceRequest = serviceRequestRepository.saveAndFlush(serviceRequest);

        return mapper.map(serviceRequest, ServiceRequestDto.class);
    }

    private Supplier<HandleException> serviceProviderNotFound() {
        return () -> new HandleException(translationService.getMessage(ERROR_SERVICE_PROVIDER_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    private Supplier<HandleException> serviceRequestNotFound() {
        return () -> new HandleException( //
                translationService.getMessage(TranslationConstants.ERROR_SERVICE_REQUEST_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public void approve(UUID serviceRequestId) {
        this.evaluateBudget(serviceRequestId, RequestStatusType.APPROVED);
    }

    public void canceled(UUID serviceRequestId) {
        this.evaluateBudget(serviceRequestId, RequestStatusType.CANCELED);
    }

    @Transactional
    void evaluateBudget(UUID serviceRequestId, RequestStatusType status) {
        final ServiceRequestEntity serviceRequest = this.serviceRequestRepository.findById(serviceRequestId)
                .orElseThrow(this.serviceRequestNotFound());

        validateClientOwner(serviceRequest);

        serviceRequest.setRequestStatus(status);
        this.serviceRequestRepository.save(serviceRequest);
    }

    private void validateClientOwner(ServiceRequestEntity serviceRequest) {
        if (serviceRequest.getServiceRequester() != userService.getCurrentUser()) {
            throw new HandleException( //
                    translationService.getMessage("Esta ordem de serviço não pertence ao usuário"), //
                    HttpStatus.FORBIDDEN //
            );
        }
    }

    private void validateServiceProviderOwner(ServiceRequestEntity serviceRequest) {
        if (serviceRequest.getServiceProvider() != userService.getCurrentUser()) {
            throw new HandleException( //
                    translationService.getMessage("Esta ordem de serviço não pertence a este prestador de serviço"), //
                    HttpStatus.FORBIDDEN //
            );
        }
    }

    @Transactional
    public void rejectService(UUID serviceRequestId) {
        final ServiceRequestEntity serviceRequest = this.serviceRequestRepository.findById(serviceRequestId) //
                .orElseThrow(this.serviceRequestNotFound());

        validateServiceProviderOwner(serviceRequest);

        serviceRequest.setRequestStatus(RequestStatusType.SERVICE_REJECTED);
        this.serviceRequestRepository.save(serviceRequest);
    }

    @Transactional
    public void finish(UUID serviceRequestId) {
        final ServiceRequestEntity serviceRequest = this.serviceRequestRepository.findById(serviceRequestId) //
                .orElseThrow(this.serviceRequestNotFound());

        validateServiceProviderOwner(serviceRequest);

        serviceRequest.setRequestStatus(RequestStatusType.DONE);
        this.serviceRequestRepository.save(serviceRequest);
    }
}
