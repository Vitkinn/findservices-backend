package com.findservices.serviceprovider.serviceprovider.controller;

import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderDto;
import com.findservices.serviceprovider.serviceprovider.service.ServiceProviderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/serviceProvider")
public class ServiceProviderController {

    @Autowired
    ServiceProviderService serviceProviderService;

    @PostMapping
    public ResponseEntity<ServiceProviderDto> createServiceProvider(@RequestBody @Valid ServiceProviderDto serviceProvider) {
        return new ResponseEntity<>(serviceProviderService.createServiceProvider(serviceProvider), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServiceProviderDto>> list() {
        return new ResponseEntity<>(serviceProviderService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ServiceProviderDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(serviceProviderService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ServiceProviderDto> update(@PathVariable UUID id, @Valid @RequestBody ServiceProviderDto country) {
        return new ResponseEntity<>(serviceProviderService.updateServiceProvider(id, country), HttpStatus.OK);
    }
}
