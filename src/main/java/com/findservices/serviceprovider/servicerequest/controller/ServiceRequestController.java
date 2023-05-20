package com.findservices.serviceprovider.servicerequest.controller;

import com.findservices.serviceprovider.servicerequest.model.ClientServiceRequestDto;
import com.findservices.serviceprovider.servicerequest.model.EvaluateRequestDto;
import com.findservices.serviceprovider.servicerequest.model.ServiceRequestDto;
import com.findservices.serviceprovider.servicerequest.service.ServiceRequestCrudService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/serviceRequest")
public class ServiceRequestController {

    ServiceRequestCrudService serviceRequestCrudService;

    @PostMapping("/create")
    public ServiceRequestDto createServiceRequest(@RequestBody @Valid ClientServiceRequestDto clientServiceRequestDto) {
        return serviceRequestCrudService.createServiceRequest(clientServiceRequestDto);
    }

    @PostMapping("/evaluate")
    public ServiceRequestDto evaluateServiceRequest(@RequestParam UUID id, @RequestBody @Valid EvaluateRequestDto evaluateRequestDto) {
        return serviceRequestCrudService.evaluateRequest(id, evaluateRequestDto);
    }

}
