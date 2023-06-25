package com.findservices.serviceprovider.servicerequest.controller;

import com.findservices.serviceprovider.servicerequest.model.ClientServiceRequestDto;
import com.findservices.serviceprovider.servicerequest.model.EvaluateRequestDto;
import com.findservices.serviceprovider.servicerequest.model.ServiceRequestDto;
import com.findservices.serviceprovider.servicerequest.model.ServiceRequestListDto;
import com.findservices.serviceprovider.servicerequest.service.ServiceRequestCrudService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/serviceRequest")
public class ServiceRequestController {

    @Autowired
    ServiceRequestCrudService serviceRequestCrudService;

    @GetMapping
    public ServiceRequestListDto listRequests() {
        return serviceRequestCrudService.listUserRequests();
    }

    @PostMapping("/create")
    public ServiceRequestDto createServiceRequest(@RequestBody @Valid ClientServiceRequestDto clientServiceRequestDto) {
        return serviceRequestCrudService.createServiceRequest(clientServiceRequestDto);
    }

    @PutMapping("/evaluate")
    public ServiceRequestDto evaluateServiceRequest(@RequestParam UUID id, @RequestBody @Valid EvaluateRequestDto evaluateRequestDto) {
        return serviceRequestCrudService.evaluateRequest(id, evaluateRequestDto);
    }

    @PutMapping("/rejectService")
    public void rejectService(@RequestParam UUID id) {
        serviceRequestCrudService.rejectService(id);
    }

    @PutMapping("/accept")
    public void accept(@RequestParam UUID id) {
        serviceRequestCrudService.approve(id);
    }

    @PutMapping("/canceled")
    public void canceled(@RequestParam UUID id) {
        serviceRequestCrudService.canceled(id);
    }

    @PutMapping("/finish")
    public void finish(@RequestParam UUID id) {
        serviceRequestCrudService.finish(id);
    }

}
